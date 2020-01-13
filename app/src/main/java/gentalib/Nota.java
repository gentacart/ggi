package gentalib;


public class Nota {
    private String idnota;
    private double totalprice;
    private double ongkir;
    private double totalbayar;
    private String alamat;
    private String namapengirim;
    private String hppengirim;
    private String namapenerima;
    private String hppenerima;
    private String listorder;
    private String confirmdate;

    public String getIdnota() {
        return idnota;
    }

    public void setIdnota(String idnota) {
        this.idnota = idnota;
    }

    public String getConfirmdate() {
        return confirmdate;
    }

    public void setConfirmdate(String confirmdate) {
        this.confirmdate = confirmdate;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public double getOngkir() {
        return ongkir;
    }

    public void setOngkir(double ongkir) {
        this.ongkir = ongkir;
    }

    public double getTotalbayar() {
        return totalbayar;
    }

    public void setTotalbayar(double totalbayar) {
        this.totalbayar = totalbayar;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamapengirim() {
        return namapengirim;
    }

    public void setNamapengirim(String namapengirim) {
        this.namapengirim = namapengirim;
    }


    public String getNamapenerima() {
        return namapenerima;
    }

    public void setNamapenerima(String namapenerima) {
        this.namapenerima = namapenerima;
    }

    public String getHppengirim() {
        return hppengirim;
    }

    public void setHppengirim(String hppengirim) {
        this.hppengirim = hppengirim;
    }

    public String getHppenerima() {
        return hppenerima;
    }

    public void setHppenerima(String hppenerima) {
        this.hppenerima = hppenerima;
    }

    public String getListorder() {
        return listorder;
    }

    public void setListorder(String listorder) {
        this.listorder = listorder;
    }
}
