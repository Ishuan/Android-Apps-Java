package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    private static final String LOCATION_SEPARATOR = " of ";

    public EarthQuakeAdapter(Context context, int resource, List<EarthQuake> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EarthQuake quake = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_detail, parent, false);
        }

        TextView quakeMagnitude = convertView.findViewById(R.id.quakeMag);
        TextView quakeDistance = convertView.findViewById(R.id.quakeDistance);
        TextView quakeCity = convertView.findViewById(R.id.quakeCity);
        TextView quakeDate = convertView.findViewById(R.id.quakeDate);
        TextView quakeTime = convertView.findViewById(R.id.quakeTime);

        GradientDrawable magnitudeCircle = (GradientDrawable) quakeMagnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = ContextCompat.getColor(getContext(), getMagnitudeColor(quake.getQuakeMagnitude()));
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        String formattedMagnitude = formatMagnitude(quake.getQuakeMagnitude());
        quakeMagnitude.setText(formattedMagnitude);

        if (quake.getQuakeCity().contains("of")) {
            String[] quakeLocation = quake.getQuakeCity().split(LOCATION_SEPARATOR);
            quakeDistance.setText(quakeLocation[0] + " " + LOCATION_SEPARATOR);
            quakeCity.setText(quakeLocation[1]);
        } else {
            quakeDistance.setText(R.string.near_the);
            quakeCity.setText(quake.getQuakeCity());
        }

        Date dateObject = new Date(quake.getQuakeTime());

        String formattedDate = formatDate(dateObject);
        quakeDate.setText(formattedDate);

        String formattedTime = formatTime(dateObject);
        quakeTime.setText(formattedTime);

        return convertView;
    }

    private int getMagnitudeColor(double quakeMagnitude) {
        switch ((int) quakeMagnitude) {
            case 0:
            case 1:
                return (R.color.magnitude1);
            case 2:
                return (R.color.magnitude2);
            case 3:
                return (R.color.magnitude3);
            case 4:
                return (R.color.magnitude4);
            case 5:
                return (R.color.magnitude5);
            case 6:
                return (R.color.magnitude6);
            case 7:
                return (R.color.magnitude7);
            case 8:
                return (R.color.magnitude8);
            case 9:
                return (R.color.magnitude9);
            default:
                return (R.color.magnitude10plus);
        }
    }

    private String formatMagnitude(double quakeMagnitude) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(quakeMagnitude);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        return simpleDateFormat.format(dateObject);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLL dd,yyyy");
        return simpleDateFormat.format(dateObject);
    }
}
