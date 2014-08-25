function kill() {
    var file = new java.io.FileWriter("tess.txt");
    file.append("Mohammed");
    file.flush();
}

function validate(value) {
 kill()
 return parseInt(value) > 10
 }