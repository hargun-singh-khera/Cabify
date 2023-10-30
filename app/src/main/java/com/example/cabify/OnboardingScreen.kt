package com.example.cabify


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class OnboardingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.on_boarding_screen)

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val dots_indicator = findViewById<WormDotsIndicator>(R.id.dots_indicator)
        val backBtn= findViewById<Button>(R.id.backBtn)
        val nextBtn= findViewById<Button>(R.id.nextBtn)
        val skipBtn = findViewById<Button>(R.id.skipBtn)
        val pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        backBtn.visibility = View.INVISIBLE
        skipBtn.visibility = View.INVISIBLE

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == 0) {
                    backBtn.visibility = View.INVISIBLE
                    skipBtn.visibility = View.INVISIBLE
                }
                else {
                    backBtn.visibility = View.VISIBLE
                    skipBtn.visibility = View.VISIBLE
                    backBtn.setOnClickListener {
                        if (viewPager.currentItem > 0) {
                            viewPager.currentItem--
                        }
                    }
                    skipBtn.setOnClickListener {
                        val intent = Intent(this@OnboardingScreen, LoginScreen::class.java)
                        startActivity(intent)
                    }
                }

                if (position == 4) {
                    nextBtn.text = "Get Started"
                    skipBtn.visibility = View.INVISIBLE
                    nextBtn.setOnClickListener {
                        val intent = Intent(this@OnboardingScreen, LoginScreen::class.java)
                        startActivity(intent)
                    }
                }
                else {
                    nextBtn.text = "Next"
                    val pageCount = viewPager.adapter?.count
                    nextBtn.setOnClickListener {
                        if (pageCount != null && viewPager.currentItem != pageCount - 1) {
                            viewPager.currentItem++
                        }
                    }
                }
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        dots_indicator.attachTo(viewPager)
    }
}