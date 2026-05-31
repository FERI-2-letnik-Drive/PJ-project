package com.example.smartmailbox.api

import android.content.SharedPreferences
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import org.json.JSONArray
import org.json.JSONObject

/**
 * A CookieJar that stores cookies in SharedPreferences so the login session
 * (the connect.sid session cookie) survives app restarts. This is what keeps
 * the user logged in on the mobile app.
 */
class PersistentCookieJar(private val prefs: SharedPreferences) : CookieJar {

    private val cookieStore = mutableMapOf<String, MutableList<Cookie>>()

    init {
        loadFromPrefs()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val host = url.host
        val current = cookieStore.getOrPut(host) { mutableListOf() }
        for (cookie in cookies) {
            current.removeAll { it.name == cookie.name }
            current.add(cookie)
        }
        saveToPrefs()
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val now = System.currentTimeMillis()
        val cookies = cookieStore[url.host] ?: return emptyList()
        val valid = cookies.filter { it.expiresAt > now }
        if (valid.size != cookies.size) {
            cookieStore[url.host] = valid.toMutableList()
            saveToPrefs()
        }
        return valid
    }

    /** Clears all stored cookies (used on logout). */
    fun clear() {
        cookieStore.clear()
        prefs.edit().remove(KEY).apply()
    }

    private fun saveToPrefs() {
        val root = JSONObject()
        for ((host, cookies) in cookieStore) {
            val arr = JSONArray()
            for (c in cookies) {
                val o = JSONObject()
                o.put("name", c.name)
                o.put("value", c.value)
                o.put("expiresAt", c.expiresAt)
                o.put("domain", c.domain)
                o.put("path", c.path)
                o.put("secure", c.secure)
                o.put("httpOnly", c.httpOnly)
                o.put("hostOnly", c.hostOnly)
                arr.put(o)
            }
            root.put(host, arr)
        }
        prefs.edit().putString(KEY, root.toString()).apply()
    }

    private fun loadFromPrefs() {
        val json = prefs.getString(KEY, null) ?: return
        try {
            val root = JSONObject(json)
            val hosts = root.keys()
            while (hosts.hasNext()) {
                val host = hosts.next()
                val arr = root.getJSONArray(host)
                val list = mutableListOf<Cookie>()
                for (i in 0 until arr.length()) {
                    val o = arr.getJSONObject(i)
                    val builder = Cookie.Builder()
                        .name(o.getString("name"))
                        .value(o.getString("value"))
                        .expiresAt(o.getLong("expiresAt"))
                        .path(o.optString("path", "/"))

                    val domain = o.optString("domain")
                    if (o.optBoolean("hostOnly", false)) {
                        builder.hostOnlyDomain(domain)
                    } else {
                        builder.domain(domain)
                    }
                    if (o.optBoolean("secure", false)) builder.secure()
                    if (o.optBoolean("httpOnly", false)) builder.httpOnly()

                    list.add(builder.build())
                }
                cookieStore[host] = list
            }
        } catch (e: Exception) {
            // Corrupt stored data -> start fresh.
            cookieStore.clear()
        }
    }

    companion object {
        private const val KEY = "cookies"
    }
}
