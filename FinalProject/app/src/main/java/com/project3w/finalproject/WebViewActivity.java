package com.project3w.finalproject;

/**
 * Created by Nate on 7/25/17.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import android.webkit.*;

public class WebViewActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.content_webview);
/*            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);*/

            // Initialize Link
            HashMap<String, String> linkInitializeOptions = new HashMap<String,String>();
            linkInitializeOptions.put("key", "3460b97ab7cc2ec7631b71647c1124");
            linkInitializeOptions.put("product", "auth");
            linkInitializeOptions.put("selectAccount", "true");
            linkInitializeOptions.put("env", "sandbox");
            linkInitializeOptions.put("clientName", "Test App");
            linkInitializeOptions.put("webhook", "http://requestb.in");
            linkInitializeOptions.put("baseUrl", "https://cdn.plaid.com/link/v2/stable/link.html");

            // If initializing Link in PATCH / update mode, also provide the public_token
            // linkInitializeOptions.put("public_token", "PUBLIC_TOKEN")

            // Generate the Link initialization URL based off of the configuration options.
            final Uri linkInitializationUrl = generateLinkInitializationUrl(linkInitializeOptions);

            System.out.println("THIS: " + linkInitializationUrl);

            // Modify Webview settings - all of these settings may not be applicable
            // or necesscary for your integration.
            final WebView plaidLinkWebview = (WebView) findViewById(R.id.webview);
            WebSettings webSettings = plaidLinkWebview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            WebView.setWebContentsDebuggingEnabled(true);

            // Initialize Link by loading the Link initiaization URL in the Webview
            plaidLinkWebview.loadUrl(linkInitializationUrl.toString());

            // Override the Webview's handler for redirects
            // Link communicates success and failure (analogous to the web's onSuccess and onExit
            // callbacks) via redirects.
            plaidLinkWebview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // Parse the URL to determine if it's a special Plaid Link redirect or a request
                    // for a standard URL (typically a forgotten password or account not setup link).
                    // Handle Plaid Link redirects and open traditional pages directly in the  user's
                    // preferred browser.
                    Uri parsedUri = Uri.parse(url);
                    if (parsedUri.getScheme().equals("plaidlink")) {
                        String action = parsedUri.getHost();
                        HashMap<String, String> linkData = parseLinkUriData(parsedUri);

                        if (action.equals("connected")) {
                            try {
                                System.out.println(linkData);
                                Log.d("Public token: ", linkData.get("public_token"));
                                Log.d("Account ID: ", linkData.get("account_id"));
                                Log.d("Account name: ", linkData.get("account_name"));
                                Log.d("Institution type: ", linkData.get("institution_type"));
                                Log.d("Institution name: ", linkData.get("institution_name"));

                            } catch (Exception e) {
                                Log.d("Error: ", e.getMessage());
                            }

                            // You will likely want to transition the view at this point.
                            plaidLinkWebview.loadUrl(linkInitializationUrl.toString());

                        } else if (action.equals("exit")) {
                            // User exited
                            // linkData may contain information about the user's status in the Link flow,
                            // the institution selected, information about any error encountered,
                            // and relevant API request IDs.
                            Log.d("User status in flow: ", linkData.get("status"));
                            // The requet ID keys may or may not exist depending on when the user exited
                            // the Link flow.
                            try {
                                Log.d("Link request ID: ", linkData.get("link_request_id"));
                                Log.d("API request ID: ", linkData.get("plaid_api_request_id"));
                            } catch (Exception e) {
                                Log.d("Error: ", e.getMessage());
                            }

                            // Reload Link in the Webview
                            // You will likely want to transition the view at this point.
                            //plaidLinkWebview.loadUrl(linkInitializationUrl.toString());

                            Intent mainIntent = new Intent(WebViewActivity.this, MainActivity.class);
                            startActivity(mainIntent);

                        } else {
                            Log.d("Link action detected: ", action);
                        }
                        // Override URL loading
                        return true;
                    } else if (parsedUri.getScheme().equals("https") ||
                            parsedUri.getScheme().equals("http")) {
                        // Open in browser - this is most  typically for 'account locked' or
                        // 'forgotten password' redirects
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        // Override URL loading
                        return true;
                    } else {
                        // Unknown case - do not override URL loading
                        return false;
                    }
                }
            });
        }

        // Generate a Link initialization URL based on a set of configuration options
        public Uri generateLinkInitializationUrl(HashMap<String,String>linkOptions) {
            Uri.Builder builder = Uri.parse(linkOptions.get("baseUrl"))
                    .buildUpon()
                    .appendQueryParameter("isWebview", "true")
                    .appendQueryParameter("isMobile", "true");
            for (String key : linkOptions.keySet()) {
                if (!key.equals("baseUrl")) {
                    builder.appendQueryParameter(key, linkOptions.get(key));
                }
            }
            try {
                URL url = new URL(URLDecoder.decode(builder.build().toString(), "UTF-8"));
                return Uri.parse(url.toString());
            } catch (Exception e) {
                Log.d("Error: ", e.getMessage());
                return null;
            }
        }

        // Parse a Link redirect URL querystring into a HashMap for easy manipulation and access
        public HashMap<String,String> parseLinkUriData(Uri linkUri) {
            HashMap<String,String> linkData = new HashMap<String,String>();
            for(String key : linkUri.getQueryParameterNames()) {
                linkData.put(key, linkUri.getQueryParameter(key));
            }
            return linkData;
        }
    }
