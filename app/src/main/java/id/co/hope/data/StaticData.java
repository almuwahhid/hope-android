package id.co.hope.data;

import id.ac.uny.riset.ride.data.model.PickerModel;

import java.util.ArrayList;
import java.util.List;

public class StaticData {
    public static List<PickerModel> dataPekerjaan(){
        List<PickerModel> dataAgama = new ArrayList<>();

        PickerModel data = new PickerModel("1", "Presiden");
        dataAgama.add(data);

        data = new PickerModel("2", "Programmer");
        dataAgama.add(data);

        data = new PickerModel("3", "Tukang Bubur");
        dataAgama.add(data);

        data = new PickerModel("4", "Tukang Jaga Parkir");
        dataAgama.add(data);

        data = new PickerModel("5", "Manager IT");
        dataAgama.add(data);

        data = new PickerModel("6", "Businessman");
        dataAgama.add(data);

        return dataAgama;
    }

    public static List<PickerModel> dataJenisKelamin(){
        List<PickerModel> dataAgama = new ArrayList<>();

        PickerModel data = new PickerModel("1", "Laki - Laki");
        dataAgama.add(data);

        data = new PickerModel("2", "Perempuan");
        dataAgama.add(data);

        return dataAgama;
    }
}
