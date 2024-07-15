{
    let url;
    (function(){
        showModalUpsert();
        deleteButton();
    }())
    
    function showModalUpsert(){
        $(".upsertBtn").click(function(event){
            event.preventDefault();
            let titleModal= document.querySelector('#upsertModalLabel');
            let id="";
            if($(this).data('id')!=null){
                id = $(this).data('id');
                url= `/categories/update`;
                titleModal.textContent="Update Category";
            }else{
                url= `/categories/insert`;
                titleModal.textContent="Insert Category";
            }
            $.ajax({
                url:  `/categories/upsert?name=${id}`,
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
    function deleteButton(){
        $(".deleteBtn").click(function(event){
            event.preventDefault();
            let form = $(this).prev(".deleteForm");
            Swal.fire({
                title: "Are you sure?",
                text: "You won't be able to revert this!",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes, delete it!"
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
    
    function alert(pesan,title,icon){
        Swal.fire({
            title: title,
            text: pesan,
            icon: icon
          });
    }

}
