<?php

require "konfigurasi.php";

$id = $_GET['id'];

$sql = "DELETE FROM tb_kontak WHERE id ={$id}";

$query = mysqli_query($con, $sql);

if ($query) {
  echo json_encode([
    'status' => 'Data Berhasil Dihapus'
  ]);
} else {
  echo json_encode([
    'status' => 'Data Gagal Dihapus'
  ]);
}
