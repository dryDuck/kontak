<?php

require "konfigurasi.php";

$sql = mysqli_query($con, "SELECT * FROM tb_kontak");
$d = array();

while ($data = mysqli_fetch_array($sql)) :
  $d[] = array(
    'id' => $data['id'],
    'nama_kontak' => $data['nama_kontak'],
    'nomor_telpon' => $data['nomor_telpon'],
    'jabatan' => $data['jabatan'],
    'alamat' => $data['alamat']
  );
endwhile;

echo json_encode(array(
  "hasil" => $d
));
