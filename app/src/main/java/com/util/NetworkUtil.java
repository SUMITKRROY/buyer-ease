package com.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;

import com.buyereasefsl.R;
import com.constant.FClientConstants;


public class NetworkUtil {
    private static final String TAG = "NetworkUtil";

    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        if (!isConnected) {
            FslLog.w(TAG, "Data network connectivity DOWN");
        }
        return isConnected;
        //activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static void hideSoftKeyboard(Activity activity) {
        if ( activity !=null && !activity.isDestroyed() && !activity.isFinishing()) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && activity.getCurrentFocus() !=null)
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void showNetworkErrorDialog(final Context context) {


        try {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
                    builder.setTitle("No Network Connectivity");
                    builder.setMessage("Kindly wait for network to restore. Otherwise please check your network settings.");
                    builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Initialization.alertDialog = null;
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            context.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //3.2.b Close the app if network is closed.
                            dialog.dismiss();
                            Initialization.alertDialog = null;
                            //System.exit(0);
                        }
                    });

                    if (Initialization.alertDialog != null) {
                        if (Initialization.alertDialog.isShowing()) {

                            GenUtils.safeAlertDialogDismiss(TAG, context, Initialization.alertDialog);
                        }
                        Initialization.alertDialog = null;
                    }
                    Initialization.alertDialog = builder.create();
                    //Initialization.alertDialog.show();
                    GenUtils.safeAlertDialogShow(TAG, context, Initialization.alertDialog);
                    //                alertDialog.show();
                }
            });
        } catch (ClassCastException e) {
            e.printStackTrace();
            FslLog.e(TAG, "context is not activity");

        }
    }

    public static void showNetworkErrorDialogWithExit(final Context context) {
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
                alertDialog.setTitle("No internet connection");
                alertDialog.setMessage("Please check your settings");
                alertDialog.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        context.startActivity(intent);
                    }
                });
                alertDialog.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //3.2.b Close the app if network is closed.
                        dialog.dismiss();
                        ((Activity) context).finishAffinity();
                        //System.exit(0);
                    }
                });
                alertDialog.show();
            }
        });

    }

    public static void showServerErrorRetryDialog(final Context context, String title) {
        String mtitle = "Temporary Error";//"Couldn't send verification SMS";
        if (title != null) {
            mtitle = title;
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);

        alertDialog.setTitle(mtitle);
        alertDialog.setMessage("Could not process request. Please try again or restart app.");

		/*alertDialog.setPositiveButton("Enable", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent=new Intent(Settings.ACTION_SETTINGS);
				context.startActivity(intent);
			}
		});*/

        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Exit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((Activity) context).finishAffinity();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public static void showServerErrorDialog(final Context context, String title) {
        String mtitle = "Temporary Server Error";//"Couldn't send verification SMS";
        if (title != null) {
            mtitle = title;
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);

        alertDialog.setTitle(mtitle);
        alertDialog.setMessage("Our server is facing some trouble in handling your request. Please restart app and try again.");


        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton("Exit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //3.2.b Close the app if network is closed.
                dialog.dismiss();
                ((Activity) context).finishAffinity();
            }
        });
        if (!((Activity) context).isFinishing()) {
            alertDialog.show();
        }

    }

    public static void showServerErrorForStageDialog(final Context context, String title) {
        String mtitle = "Temporary Server Error";//"Couldn't send verification SMS";
        if (title != null) {
            mtitle = title;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);

        builder.setTitle(mtitle);
        builder.setMessage("Our server is facing some trouble in handling your request. Please restart app and try again.");

		/*alertDialog.setPositiveButton("Enable", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent=new Intent(Settings.ACTION_SETTINGS);
				context.startActivity(intent);
			}
		});*/

        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //3.2.b Close the app if network is closed.
                dialog.dismiss();
                Initialization.alertDialog = null;
                ((Activity) context).finishAffinity();
            }
        });
//anand
        if (Initialization.alertDialog != null) {
            if (Initialization.alertDialog.isShowing())
//                Initialization.alertDialog.dismiss();
                GenUtils.safeAlertDialogDismiss(TAG, context, Initialization.alertDialog);
            Initialization.alertDialog = null;
        }
        Initialization.alertDialog = builder.create();
        GenUtils.safeAlertDialogShow(TAG, context, Initialization.alertDialog);

//        alertDialog.show();
    }

    public static void showSMSErrorDialog(final Activity context, String title) {
        String mtitle = "Temporary Server Error";//"Couldn't send verification SMS";
        if (title != null) {
            mtitle = title;
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);

        alertDialog.setTitle(mtitle);
        alertDialog.setMessage("Our server is having some temporary problem. Please restart app and try again.");

		/*alertDialog.setPositiveButton("Enable", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent=new Intent(Settings.ACTION_SETTINGS);
				context.startActivity(intent);
			}
		});*/

        alertDialog.setPositiveButton("Exit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //3.2.b Close the app if network is closed.
                //System.exit(0);
                dialog.dismiss();
                context.finishAffinity();
            }
        });
        alertDialog.show();
    }


    public interface SessionCloseListner {
        public void onSessionClosed();
    }

    public static void showSessionTimeDialog(final Context context, String title, String msg, final SessionCloseListner ss) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Initialization.alertDialog = null;
                ss.onSessionClosed();
            }
        });
/*        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/

        //builder.show();
        if (Initialization.alertDialog != null) {
            if (Initialization.alertDialog.isShowing())
//                Initialization.alertDialog.dismiss();
                GenUtils.safeAlertDialogDismiss(TAG, context, Initialization.alertDialog);
            Initialization.alertDialog = null;
        }
        Initialization.alertDialog = builder.create();
        GenUtils.safeAlertDialogShow(TAG, context, Initialization.alertDialog);
        /*if (!((Activity) context).isFinishing()) {
            Initialization.alertDialog.show();
        }*/
    }

    public static void showSessionExpiredDialog(final Context context, String msg, final SessionCloseListner ss) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
//        alertDialog.setTitle("Session Expired ");
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(FClientConstants.ACTION_OK, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ss.onSessionClosed();

            }
        });

        //alertDialog.show();
        if (Initialization.alertDialog != null) {
            if (Initialization.alertDialog.isShowing())
                Initialization.alertDialog.dismiss();
            Initialization.alertDialog = null;
        }
        Initialization.alertDialog = alertDialog.create();

        if (!((Activity) context).isFinishing()) {
            Initialization.alertDialog.show();
        }
    }

    public interface CancelInstantRideShareListner {
        void onOkClicked();

        void onCancelClicked();
    }

    public static void showCancelRideSharingDialog(final Activity activity, String title, final CancelInstantRideShareListner cc) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
        builder.setMessage(title);
        builder.setPositiveButton(FClientConstants.TEXT_BUTTON_YES, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Initialization.alertDialog = null;
                cc.onOkClicked();

            }
        });
        builder.setNegativeButton(FClientConstants.TEXT_BUTTON_NO, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Initialization.alertDialog = null;
                cc.onCancelClicked();
            }
        });
//        alertDialog.show();
        //Anand storing dialog to confirm end ride at central place

        //dialog is acted upon
        if (Initialization.alertDialog != null) {
            if (Initialization.alertDialog.isShowing())
                GenUtils.safeAlertDialogDismiss(TAG, activity, Initialization.alertDialog);
//                Initialization.alertDialog.dismiss();
            Initialization.alertDialog = null;
        }
        Initialization.alertDialog = builder.create();

        if (!(activity.isFinishing())) {
            Initialization.alertDialog.show();
        }
    }


   /* public static void networkErrorDialog(final Context context, String title) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        alertDialog.setTitle(title);
        alertDialog.setMessage(context.getResources().getString(R.string.our_server_had_some_problem));

        alertDialog.setPositiveButton(context.getResources().getString(R.string.try_again), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.dismiss();
                ((Activity) context).finishAffinity();

            }
        });

        alertDialog.show();
    }
*/
/*    public static void failedServerDialog(final Context context, String title, String msg, final OnCancellationFailed cancellationFailed) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);

        alertDialog.setPositiveButton(context.getResources().getString(R.string.try_again), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                cancellationFailed.onCancellationFail();


            }
        });

        alertDialog.show();
    }*/

    public static void ErrorOnInstantRideRequestDialog(final Activity context, String title, String message, final getSearchAgain getSearchAgain) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(FClientConstants.ACTION_GOT_IT, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSearchAgain.onSearchAgain();
                dialog.dismiss();


            }
        });

        alertDialog.show();
    }

    public interface getSearchAgain {
        void onSearchAgain();
    }

    public static void showRequestFailedDialog(final Activity context, String title, String subTitle, final OnCancellationFailed cancellationFailed) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        alertDialog.setTitle(title);
        alertDialog.setMessage(subTitle);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancellationFailed.onCancellationFail();
            }
        });

        alertDialog.show();
    }

    public static void showFailedFromServerDialog(final Activity context, String message, final OnCancellationFailed cancellationFailed) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancellationFailed.onCancellationFail();
            }
        });

        alertDialog.show();
    }

    public interface OnCancellationFailed {
        void onCancellationFail();
    }

    public static void showServerNotRespondingDialog(final Activity context, String title, final OnCancellationFailed onCancellationFailed) {
        String mtitle = "Server Not Responding";//"Couldn't send verification SMS";
        if (title != null) {
            mtitle = title;
        }

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);

        alertDialog.setTitle(mtitle);
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Our server is having some problem. Please try after sometime");
        alertDialog.setPositiveButton("Exit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //3.2.b Close the app if network is closed.
                dialog.dismiss();
                onCancellationFailed.onCancellationFail();
                ((Activity) context).finishAffinity();

            }
        });
        alertDialog.show();
    }

/*    public static void showDialogToConfirm(final Activity context, String title, String msg, final CancelInstantRideShareListner cc) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton(FClientConstants.TEXT_BUTTON_YES, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                cc.onOkClicked();

            }
        });
        alertDialog.setNegativeButton(FClientConstants.TEXT_BUTTON_NO, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                cc.onCancelClicked();
            }
        });
        alertDialog.show();
    }*/


}
