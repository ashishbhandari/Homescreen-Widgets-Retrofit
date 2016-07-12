package com.retrofitwithwidgets.provider;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.retrofitwithwidgets.R;
import com.retrofitwithwidgets.web.QuoteAPIService;
import com.retrofitwithwidgets.web.model.QuoteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ashish (Min2) on 07/07/16.
 */
public class MyWidgetProvider extends AppWidgetProvider {

    private static final String TAG = MyWidgetProvider.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent widgetIntent) {
        final String action = widgetIntent.getAction();
        Log.d(TAG, "action received: " + action);
        super.onReceive(context, widgetIntent);
    }

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "updating app widget");
        // To prevent any ANR timeouts, we perform the update in a Retrofit style or create another service
        QuoteAPIService gitHubService = QuoteAPIService.retrofit.create(QuoteAPIService.class);
        Call<QuoteResponse> call = gitHubService.getTodayThought();
        call.enqueue(new Callback<QuoteResponse>() {
            @Override
            public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {
                Log.d(TAG, "Response: " + response.message());

                RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);

                updateViews.setTextViewText(R.id.quote_description, response.body().quote.text);
                updateViews.setTextViewText(R.id.author_name, response.body().quote.author);

                // Notify the widget that the list view needs to be updated.
                final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
                final ComponentName cn = new ComponentName(context, MyWidgetProvider.class);
                mgr.updateAppWidget(cn, updateViews);

            }

            @Override
            public void onFailure(Call<QuoteResponse> call, Throwable t) {
                Log.d(TAG, "Failure Response: " + t.getMessage());
            }
        });
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}
