<?php
require "konfigurasi.php";

$namaKontak = $_GET['q'];

$sql = "SELECT * FROM tb_kontak WHERE nama_kontak LIKE '%{$namaKontak}%'";

$query = mysqli_query($con, $sql);

$data = [];

while ($hasil = mysqli_fetch_array($query)) {
  $data[] = [
    'id' => $hasil['id'],
    'nama_kontak' => $hasil['nama_kontak'],
    'nomor_telpon' => $hasil['nomor_telpon']
  ];
}
echo json_encode(array(
  "hasil" => $data
));
