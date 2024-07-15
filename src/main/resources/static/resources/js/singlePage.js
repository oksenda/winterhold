(function(){
    home()
    btnHome();
    btnAuthor();

}())

function btnHome(){
    $("#btnHome").click(function(event){
        event.preventDefault();
        home()

    });
}
function btnAuthor(){
    $("#btnAuthor").click(function(event){
        event.preventDefault();
        author()

    });
}
function home(){
    $.ajax({
        url: `/home`,
        method: "GET",
        success: function(data) {
            $("#loadMainContent").html(data);
        },
        error: function(xhr) {
            alert("Gagal mengambil data");
            console.log(xhr);
        }
    });
}

function author(){
    $.ajax({
        url: `/authors?pageNumber=1`,
        method: "GET",
        success: function(data) {
            $("#loadMainContent").html(data);
        },
        error: function(xhr) {
            alert("Gagal mengambil data");
            console.log(xhr);
        }
    });
}