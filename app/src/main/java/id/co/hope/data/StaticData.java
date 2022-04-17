package id.co.hope.data;

import id.ac.uny.riset.ride.data.model.PickerModel;

import java.util.ArrayList;
import java.util.List;

public class StaticData {
    public static List<PickerModel> dataPekerjaan(){
        List<PickerModel> dataAgama = new ArrayList<>();

        PickerModel data = new PickerModel("1", "Public Relation/ Humas");
        dataAgama.add(data);

        data = new PickerModel("2", "Perawat");
        dataAgama.add(data);

        data = new PickerModel("3", "Content Creator");
        dataAgama.add(data);

        data = new PickerModel("4", "Animator");
        dataAgama.add(data);

        data = new PickerModel("5", "Pengacara");
        dataAgama.add(data);

        data = new PickerModel("6", "Staff Pendidik (Dosen/Guru)");
        dataAgama.add(data);

        data = new PickerModel("7", "Akuntan");
        dataAgama.add(data);

        data = new PickerModel("8", "Digital Marketers");
        dataAgama.add(data);

        data = new PickerModel("9", "Arsitek");
        dataAgama.add(data);

        data = new PickerModel("10", "PNS (di instansi pendidikan maupun pemerintahan)");
        dataAgama.add(data);

        data = new PickerModel("11", "Lain - Lain");
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
