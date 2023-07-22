/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package uas_pbo;
import java.util.ArrayList;
import java.util.Scanner;


class Buku {
    protected String judul;
    protected int hargaBeli;
    protected int hargaJual;
    protected int jumlahStok;

    public Buku(String judul, int hargaBeli, int hargaJual, int jumlahStok) {
        this.judul = judul;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.jumlahStok = jumlahStok;
    }

    public void tambahStok(int jumlah) {
        jumlahStok += jumlah;
    }

    public void kurangiStok(int jumlah) {
        jumlahStok -= jumlah;
        if (jumlahStok <= 0) {
            System.out.println("Stok buku '" + judul + "' telah mencapai 0.");
        }
    }

    public String getJudul() {
        return judul;
    }

    public int getHargaBeli() {
        return hargaBeli;
    }

    public int getHargaJual() {
        return hargaJual;
    }

    public int getJumlahStok() {
        return jumlahStok;
    }
}

// Kelas BukuFiksi sebagai turunan dari kelas Buku
class BukuFiksi extends Buku {
    public BukuFiksi(String judul, int hargaBeli, int hargaJual, int jumlahStok) {
        super(judul, hargaBeli, hargaJual, jumlahStok);
    }
}

// Kelas BukuNonFiksi sebagai turunan dari kelas Buku
class BukuNonFiksi extends Buku {
    public BukuNonFiksi(String judul, int hargaBeli, int hargaJual, int jumlahStok) {
        super(judul, hargaBeli, hargaJual, jumlahStok);
    }
}

// Kelas Majalah sebagai turunan dari kelas Buku
class Majalah extends Buku {
    private int nomorEdisi;

    public Majalah(String judul, int hargaBeli, int hargaJual, int jumlahStok, int nomorEdisi) {
        super(judul, hargaBeli, hargaJual, jumlahStok);
        this.nomorEdisi = nomorEdisi;
    }

    public int getNomorEdisi() {
        return nomorEdisi;
    }
}

public class ManajemenPenjualanBuku {
    private static int modalAwal = 1000000; // Modal awal
    private static int modalBerjalan = modalAwal;
    private static ArrayList<Buku> daftarBuku = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pilihan;

        do {
            tampilkanMenu();
            pilihan = scanner.nextInt();
            scanner.nextLine(); // Membersihkan karakter newline dari input sebelumnya

            switch (pilihan) {
                case 1:
                    tampilkanLaporan();
                    break;
                case 2:
                    tampilkanStokBuku();
                    break;
                case 3:
                    tambahPenjualan();
                    break;
                case 4:
                    tambahPembelian();
                    break;
                case 5:
                    // Keluar dari program
                    System.out.println("Terima kasih telah menggunakan program Manajemen Penjualan Buku!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih opsi yang benar.");
            }
        } while (pilihan != 5);
    }

    private static void tampilkanMenu() {
        System.out.println("*****************************************");
        System.out.println("* Sistem Penjualan Buku *");
        System.out.println("* By : Yongky Bayu P , 21201275");
        System.out.println("*****************************************");
        System.out.println("===== Menu Manajemen Penjualan Buku =====");
        System.out.println("1. Tampilkan Laporan");
        System.out.println("2. Tampilkan Stok Buku");
        System.out.println("3. Tambah Penjualan");
        System.out.println("4. Tambah Pembelian");
        System.out.println("5. Keluar");
        System.out.print("Pilihan Anda: ");
    }

    private static void tampilkanLaporan() {
        System.out.println("===== Laporan =====");
        System.out.println("Modal Awal: " + modalAwal);
        System.out.println("Modal Berjalan: " + modalBerjalan);
    }

    private static void tampilkanStokBuku() {
        System.out.println("===== Stok Buku =====");
        for (Buku buku : daftarBuku) {
            String jenisBuku = (buku instanceof Majalah) ? "Majalah" : ((buku instanceof BukuFiksi) ? "Buku Fiksi" : "Buku Non-Fiksi");
            System.out.println(jenisBuku + " - Judul: " + buku.getJudul() + ", Stok: " + buku.getJumlahStok());
            if (buku instanceof Majalah) {
                System.out.println("Nomor Edisi: " + ((Majalah) buku).getNomorEdisi());
            }
        }
    }

    private static void tambahPenjualan() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan judul buku yang dijual: ");
        String judul = scanner.nextLine();

        Buku bukuDijual = cariBuku(judul);
        if (bukuDijual != null) {
            System.out.print("Masukkan jumlah buku yang dijual: ");
            int jumlahDijual = scanner.nextInt();

            if (bukuDijual.getJumlahStok() >= jumlahDijual) {
                int totalPendapatan = jumlahDijual * bukuDijual.getHargaJual();
                modalBerjalan += totalPendapatan;
                bukuDijual.kurangiStok(jumlahDijual);
                System.out.println("Penjualan berhasil. Total pendapatan: " + totalPendapatan);
            } else {
                System.out.println("Stok buku tidak mencukupi.");
            }
        } else {
            System.out.println("Buku dengan judul '" + judul + "' tidak ditemukan.");
        }
    }

    private static void tambahPembelian() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan judul buku yang dibeli: ");
        String judul = scanner.nextLine();

        Buku bukuDibeli = cariBuku(judul);
        if (bukuDibeli != null) {
            System.out.print("Masukkan jumlah buku yang dibeli: ");
            int jumlahDibeli = scanner.nextInt();
            int totalBiaya = jumlahDibeli * bukuDibeli.getHargaBeli();

            if (modalBerjalan >= totalBiaya) {
                modalBerjalan -= totalBiaya;
                bukuDibeli.tambahStok(jumlahDibeli);
                System.out.println("Pembelian berhasil. Total biaya: " + totalBiaya);
            } else {
                System.out.println("Modal berjalan tidak mencukupi.");
            }
        } else {
            System.out.println("Buku dengan judul '" + judul + "' tidak ditemukan.");
        }
    }

    private static Buku cariBuku(String judul) {
        for (Buku buku : daftarBuku) {
            if (buku.getJudul().equalsIgnoreCase(judul)) {
                return buku;
            }
        }
        return null;
    }

    // Method untuk menambahkan buku ke daftarBuku
    private static void tambahBuku(Buku buku) {
        daftarBuku.add(buku);
    }

    // Method untuk menambahkan buku fiksi ke daftarBuku
    private static void tambahBukuFiksi(String judul, int hargaBeli, int hargaJual, int jumlahStok) {
        Buku buku = new BukuFiksi(judul, hargaBeli, hargaJual, jumlahStok);
        tambahBuku(buku);
    }

    // Method untuk menambahkan buku non fiksi ke daftarBuku
    private static void tambahBukuNonFiksi(String judul, int hargaBeli, int hargaJual, int jumlahStok) {
        Buku buku = new BukuNonFiksi(judul, hargaBeli, hargaJual, jumlahStok);
        tambahBuku(buku);
    }

    // Method untuk menambahkan majalah ke daftarBuku
    private static void tambahMajalah(String judul, int hargaBeli, int hargaJual, int jumlahStok, int nomorEdisi) {
        Buku buku = new Majalah(judul, hargaBeli, hargaJual, jumlahStok, nomorEdisi);
        tambahBuku(buku);
    }

    // Method untuk menginisialisasi data awal buku
    private static void inisialisasiDataBuku() {
        tambahBukuFiksi("Buku Fiksi 1", 50000, 70000, 10);
        tambahBukuFiksi("Buku Fiksi 2", 45000, 65000, 8);
        tambahBukuNonFiksi("Buku Non-Fiksi 1", 55000, 75000, 12);
        tambahBukuNonFiksi("Buku Non-Fiksi 2", 60000, 80000, 15);
        tambahMajalah("Majalah 1", 20000, 30000, 20, 5);
        tambahMajalah("Majalah 2", 25000, 35000, 18, 6);
    }



    
}
