{
    let url="/loans/upsert";
    (function(){
        showModalUpsert();
        returnButton();
        showModalDetail();
    }())
    
    function showModalUpsert(){
        $(".upsertBtn").click(function(event){
            event.preventDefault();
            let titleModal= document.querySelector('#upsertModalLabel');
            let id="";
            if($(this).data('id')!=null){
                id = $(this).data('id');
                titleModal.textContent="Update Loan";
            }else{
                titleModal.textContent="Insert Loan";
            }
            $.ajax({
                url:  `/loans/upsert?id=${id}`,
                method: "GET",
                success: function(data) {
                    $('#upsertModal').modal('show');
                    $("#loadFormUpsert").html(data);
                    submitUpsert();
                },
                error: function(xhr) {
                    alert("Gagal mengambil data");
                    console.log(xhr);
                }
            });
    
        });
    }
    function returnButton(){
        $(".returnBtn").click(function(event){
            event.preventDefault();
            let form = $(this).next(".returnForm");
            Swal.fire({
                title: "Are you sure?",
                text: "You won't be able to return this!",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes, return it!"
              }).then((result) => {
                if (result.isConfirmed) {
                    form.submit();
                }
              });
        });
    }
    
    function submitUpsert(){
        $('#upsertForm').submit(function(event) {
            event.preventDefault(); 
            let formData = $(this).serialize();
            console.log("data: "+formData);
            $.ajax({
                url: url,
                method: 'POST',
                data: formData,
                success: function(response) {
                    if(response.includes('<html')){
                        location.reload();               
                    }else{
                        $("#loadFormUpsert").html(response);
                        submitUpsert();   
                    }
                    console.log(response);
                },
                error: function(response) {
                    console.error('Gagal mengirimkan formulir dengan AJAX');
                    console.log(response);
                }
            });
        });
    }

    function showModalDetail(){
        $(".detailBtn").click(function(event){
            event.preventDefault();
            let id=$(this).data('id');
            $.ajax({
                url:  `/loans/detail?id=${id}`,
                method: "GET",
                success: function(data) {
                    $('#detailModal').modal('show');
                    $("#loadFormDetail").html(data);
                    submitUpsert();
                },
                error: function(xhr) {
                    alert("Gagal mengambil data");
                    console.log(xhr);
                }
            });
    
        });
    }
    
    function alert(pesan,title,icon){
        Swal.fire({
            title: title,
            text: pesan,
            icon: icon
          });
    }

}
