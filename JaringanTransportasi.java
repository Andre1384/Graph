package KasusNyata;

import java.util.Arrays;
import java.util.Comparator;

public class JaringanTransportasi {
    private int jumlahLokasi, jumlahJalan;
    private Jalan[] jalan;

    //Konstruktor
    public JaringanTransportasi(int v, int e) {
        jumlahLokasi = v; // Vertex
        jumlahJalan = e; // Edge
        jalan = new Jalan[e];
    }

    // Metode untuk menemukan subset dari elemen i
    int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    // Metode untuk menggabungkan dua subset
    void union(Subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank) {
            subsets[xroot].parent = yroot;
        } else if (subsets[xroot].rank > subsets[yroot].rank) {
            subsets[yroot].parent = xroot;
        } else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    // Metode utama untuk menjalankan algoritma kruskal
    void kruskalMST() {
        Jalan[] hasil = new Jalan[jumlahLokasi]; // Untuk menyimpan hasil MST
        int e = 0; // Indeks untuk hasil
        int i = 0; // Indeks untuk jalan yang diurutkan
        int totalBiaya = 0;

        // Mengurutkan jalan berdasarkan biaya
        Arrays.sort(jalan, Comparator.comparingInt(j -> j.biaya));

        // Membuat subsets untuk jumlahLokasi
        Subset[] subsets = new Subset[jumlahLokasi];
        for (int v = 0; v < jumlahLokasi; ++v) {
            subsets[v] = new Subset();
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        // Mengambil jalan terendah dan menambahkannya ke MST
        while (e < jumlahLokasi - 1) {
            Jalan jalanBerikutnya = jalan[i++];

            int x = find(subsets, jalanBerikutnya.sumber);
            int y = find(subsets, jalanBerikutnya.tujuan);

            // Jika tidak membentuk siklus, tambahkan jalan ke hasil
            if (x != y) {
                hasil[e++] = jalanBerikutnya;
                totalBiaya += jalanBerikutnya.biaya;
                union(subsets, x, y);
            }
        }

        // Menampilkan hasil MST
        System.out.println("Jaringan Transportasi Minimum (MST):");
        for (int j = 0; j < e; j++) {
            System.out.println("Lokasi " + hasil[j].sumber + " -- Lokasi " + hasil[j].tujuan + " dengan biaya " + hasil[j].biaya);
        }

        // Menampilkan total biaya
        System.out.println("Total biaya untuk jaringan transportasi: " + totalBiaya);
    }

    public static void main(String[] args) {
        int jumlahLokasi = 5; // Jumlah lokasi
        int jumlahJalan = 7; // Jumlah jalan
        JaringanTransportasi jaringan = new JaringanTransportasi(jumlahLokasi, jumlahJalan);

        // Menambahkan jalan dan biaya
        jaringan.jalan[0] = new Jalan(0, 1, 10);
        jaringan.jalan[1] = new Jalan(0, 2, 6);
        jaringan.jalan[2] = new Jalan(0, 3, 5);
        jaringan.jalan[3] = new Jalan(1, 3, 15);
        jaringan.jalan[4] = new Jalan(2, 3, 4);
        jaringan.jalan[5] = new Jalan(1, 2, 12);
        jaringan.jalan[6] = new Jalan(3, 4, 8);

        // Menjalankan algoritma Trial.Kruskal
        jaringan.kruskalMST();
    }
}
