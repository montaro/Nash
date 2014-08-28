function kill() {
    var file = new java.io.FileWriter("tess.txt");
    file.append("Ahmed");
    file.flush();
}
kill()