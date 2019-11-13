package id.co.hope.app.survey.model;

public class SurveyPayloadModel {
    Boolean survey, sub_aspek, indikator, aspek;
    String data_aspek, data_aspek_indicator, data_survey, result;

    public Boolean getAspek() {
        return aspek;
    }

    public Boolean getSurvey() {
        return survey;
    }

    public Boolean getSub_aspek() {
        return sub_aspek;
    }

    public Boolean getIndikator() {
        return indikator;
    }

    public String getData_aspek() {
        return data_aspek;
    }

    public String getData_aspek_indicator() {
        return data_aspek_indicator;
    }

    public String getData_survey() {
        return data_survey;
    }

    public String getResult() {
        return result;
    }
}
