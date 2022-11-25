package com.alfabank.qapp

import android.support.test.rule.ActivityTestRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alfabank.qapp.presentation.MainActivity
import org.junit.Rule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.alfabank.qapp", appContext.packageName)
    }

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testPositiveLogin() {
        //Given
        onView(withHint("Логин")).check(matches(withText("")))
        onView(withHint("Пароль")).check(matches(withText("")))
        onView(withHint("Вход в Alfa-Test")).check(matches(isDisplayed()))

        //When
        onView(withHint("Логин")).perform(typeText("Login"))
        onView(withHint("Пароль")).perform(typeText("Password"))
        onView(withHint("Вход")).perform(click())

        //Then
        onView(withHint("Вход в Alfa-Test выполнен")).check(matches(isDisplayed()))
    }

    @Test
    fun testInvalidLogin() {
        //Given
        onView(withHint("Логин")).perform(typeText("@@@@"))
        onView(withHint("Пароль")).perform(typeText("Password"))

        //When
        onView(withHint("Вход")).perform(click())

        //Then
        onView(withHint("InvalidValue")).check(matches(isDisplayed()))
    }

    @Test
    fun testInvalidPassword() {
        //Given
        onView(withHint("Логин")).perform(typeText("Login"))
        onView(withHint("Пароль")).perform(typeText("@@@@"))

        //When
        onView(withHint("Вход")).perform(click())

        //Then
        onView(withHint("InvalidValue")).check(matches(isDisplayed()))
    }

    @Test
    fun testLoginWithLongData() {
        //Given
        var login = "A".repeat(50)
        onView(withHint("Логин")).perform(typeText(login + "12"))

        //When
        onView(withHint("Вход")).perform(click())

        //Then
        onView(withHint("ExceptValue")).check(matches(isDisplayed()))

        //And
        onView(withHint("Логин")).check(matches(withText(login)))
    }


    @Test
    fun testPasswordWithLongData() {
        //Given
        var password = "A".repeat(50)
        onView(withHint("Пароль")).perform(typeText(password + "12"))

        //When
        onView(withHint("Вход")).perform(click())

        //Then
        onView(withHint("ExceptValue")).check(matches(isDisplayed()))

        //And
        onView(withHint("Пароль")).check(matches(withText(password)))
    }
}
