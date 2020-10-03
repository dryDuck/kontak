<?php

require 'konfigurasi.php';

$nama = $_POST['nama_kontak'];
// $nama = 'Ayam';
$nomor_telpon = $_POST['nomor_telpon'];
// $nomor_telpon = 'Nomor Telpon';
$jabatan = $_POST['jabatan'];
// $jabatan = 'Jabatan';
$alamat = $_POST['alamat'];
// $alamat = 'Alamat';

$sql = "INSERT INTO tb_kontak (`id`, `nama_kontak`, `nomor_telpon`, `jabatan`, `alamat`) VALUES (NULL, '{$nama}', '{$nomor_telpon}', '{$jabatan}', '{$alamat}');";

$insert = mysqli_query($con, $sql);
if ($insert) {
  echo json_encode(array('status' => 'oke'));
} else {
  echo json_encode(array('status' => 'gagal'));
};

mysqli_close($con);
// INSERT INTO `tb_kontak` (`id`, `nama_kontak`, `nomor_telpon`, `jabatan`, `alamat`) VALUES (NULL, 'Andry', '081363848517', 'Manusia', 'Hutan');
// INSERT INTO `tb_kontak` (`id`, `nama_kontak`, `nomor_telpon`, `jabatan`, `alamat`) VALUES (NULL, 'Andry', '081363848517', 'Manusia', 'Hutan');