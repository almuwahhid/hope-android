package id.co.hope.data.model;

import id.co.hope.app.survey.model.PertanyaanSurveyModel;

import java.util.List;

public class DetailSurveyModel {
    List<PertanyaanSurveyModel> listPertanyaan;
    String nama_indikator;

    public List<PertanyaanSurveyModel> getListPertanyaan() {
        return listPertanyaan;
    }
}
