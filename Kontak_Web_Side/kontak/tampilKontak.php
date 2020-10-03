<?php
require "konfigurasi.php";

$id = $_GET['id'];

$sql = "SELECT * FROM tb_kontak WHERE id = {$id}";

$query = mysqli_query($con, $sql);

$hasil = mysqli_fetch_assoc($query);

echo json_encode([
  'namaKontak' => $hasil['nama_kontak'],
  'nomor_telpon' => $hasil['nomor_telpon'],
  'jabatan' => $hasil['jabatan'],
  'alamat' => $hasil['alamat']
]);
