package com.sendbizcard.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface

import android.content.Intent

import android.location.Geocoder

import android.widget.Toast

import android.location.LocationManager

import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.FragmentActivity
import java.io.IOException
import java.util.*


class LocationGetter(
   val mContext: Activity,
   val REQUEST_LOCATION: Int,
   val locationManager: LocationManager?
) {


   // private var mContext: Activity? = null
    private var geocoder: Geocoder? = null

   /* fun LocationGetter(
        mContext: Activity?,
        requestLocation: Int,
        locationManager: LocationManager?
    ) {
        this.mContext = mContext
        this.locationManager = locationManager
        this@LocationGetter.REQUEST_LOCATION = requestLocation
    }
*/

    fun getLocation() : String {
        var address = ""
        if (ActivityCompat.checkSelfPermission(
                mContext!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                mContext!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            val LocationGps: Location? =
                locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val LocationNetwork: Location? =
                locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            val LocationPassive: Location? =
                locationManager!!.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            if (LocationGps != null) {
                val lat: Double = LocationGps.getLatitude()
                val longi: Double = LocationGps.getLongitude()
                address= getTheAddress(lat, longi)
            } else if (LocationNetwork != null) {
                val lat: Double = LocationNetwork.getLatitude()
                val longi: Double = LocationNetwork.getLongitude()
                address= getTheAddress(lat, longi)
            } else if (LocationPassive != null) {
                val lat: Double = LocationPassive.getLatitude()
                val longi: Double = LocationPassive.getLongitude()
                address= getTheAddress(lat, longi)
            } else {
                Toast.makeText(mContext, "Can't Get Your Location", Toast.LENGTH_SHORT).show()
            }
        }
        return address
    }

    private fun getTheAddress(latitude: Double, longitude: Double): String {
        var addresses: List<Address>? = null
        var address=""
        geocoder = Geocoder(mContext, Locale.getDefault())
        try {
            addresses = geocoder!!.getFromLocation(latitude, longitude, 1)
             address = addresses[0].getAddressLine(0)+ addresses[0].getPostalCode()+addresses[0].getLocality()+addresses[0].getAdminArea()
            val city: String = addresses[0].getLocality()
            val state: String = addresses[0].getAdminArea()
            val country: String = addresses[0].getCountryName()
            val postalCode: String = addresses[0].getPostalCode()
            val knownName: String = addresses[0].getFeatureName()
            Log.d("neel", address)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return address
    }

    fun OnGPS() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(mContext)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES",
            DialogInterface.OnClickListener { dialog, which ->
                mContext!!.startActivity(
                    Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    )
                )
            }).setNegativeButton("NO",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}