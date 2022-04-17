package id.co.hope.data;


import id.co.hope.BuildConfig;

public class EndPoints {
    public static String a = BuildConfig.base_url;
//    public static String a = "";
    private static String b = BuildConfig.api;
//    private static String b = "";
    private static String c = a+b;

    public static String stringLogin() {
        return c + BuildConfig.login;
    }
//
    public static String stringRegister() {
        return c + BuildConfig.register;
    }

    public static String stringArtikel() {
        return c + BuildConfig.getArtikel;
    }
//
    public static String stringCheckSurvey() {
        return c + BuildConfig.checkSurvey;
    }
//
    public static String stringMakeSurvey() {
        return c + BuildConfig.makeSurvey;
    }
//
    public static String stringUpdateBiodata() {
        return c + BuildConfig.biodata;
    }

    public static String stringUbahPasswod() {
        return c + BuildConfig.ubahPassword;
    }
//
    public static String stringPernyataan() {
        return c + BuildConfig.pernyataan;
    }
//
//    public static String stringDetailSurveySaya() {
//        return c + BuildConfig.getDetailSurveySaya;
//    }
//
//    public static String stringNilaiSurvey() {
//        return c + BuildConfig.getNilaiSurvey;
//    }
//
//    public static String stringNilaiSurveyByAspek() {
//        return c + BuildConfig.getNilaiSurveyByAspek;
//    }
//
    public static String stringPertanyaanSaya() {
        return c + BuildConfig.getPertanyaan;
    }
//
    public static String stringSubmitPertanyaan() {
        return c + BuildConfig.submitPertanyaan;
    }
//
    public static String stringSurveySaya() {
        return c + BuildConfig.getSurveySaya;
    }
//
    public static String stringUpdateTaskPertanyaan() {
        return c + BuildConfig.updateTaskPertanyaan;
    }
//
    public static String stringLupaPassword() {
        return c + BuildConfig.lupaPassword;
    }

//    public static String stringCheckIntervensiToday() {
//        return c + BuildConfig.checkIntervensiToday;
//    }
    public static String stringCheckIntervensiToday() {
        return c + BuildConfig.checkIntervensiToday;
    }


}
