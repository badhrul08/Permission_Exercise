package com.binar.permissionsample

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.binar.permissionsample.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loadImage.setOnClickListener {
            loadImage()
        }

        binding.requestLocation.setOnClickListener {
          requestPermissionLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            201 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(this, "ALLOW telah dipilih", Toast.LENGTH_LONG).show()
                    getLongLat()
                } else {
                    Toast.makeText(this, "DENY telah dipilih", Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                Toast.makeText(this, "Bukan Request yang dikirim", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loadImage(){
        Glide.with(this)
            .load("https://img.icons8.com/plasticine/2x/flower.png")
            .circleCrop()
            .into(binding.imageView)

        val snackBar = Snackbar.make(binding.mainActivity, "Ini Snackbar Foto", Snackbar.LENGTH_LONG)

        snackBar.setAction("Undo"){
            snackBar.dismiss()
        }

        snackBar.show()
    }

    private fun requestLocationPermission(){
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 201)
    }

    private fun requestPermissionLocation(){
        val permissionCheck = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Location DIIZINKAN", Toast.LENGTH_LONG).show()
            getLongLat()
        } else {
            Toast.makeText(this, "Permission Location DITOLAK", Toast.LENGTH_LONG).show()
            requestLocationPermission()
        }
    }

    @SuppressLint("MissingPermission")
    fun getLongLat(){
        val locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val location: Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        val latLongText = "Lat: ${location?.latitude} Long : ${location?.longitude}"
        Log.d("Nilai Lat Long", latLongText)
        Toast.makeText(this, "Lat: ${location?.latitude} Long : ${location?.longitude}", Toast.LENGTH_LONG).show()
        }
    }
