$(function() {
  $( "#tabs" ).tabs();

  $("#previewLink").on("click", function() {
    var formData = {
            'name'              : $('input[name=name]').val(),
            'body'              : $('textarea[name=body]').val(),
            'type'              : $('select[name=type]').val()
        };

    // process the form
    $.ajax({
        type        : 'POST', // define the type of HTTP verb we want to use (POST for our form)
        url         : '/api/preview', // the url where we want to POST
        data        : formData, // our data object
        //dataType    : 'json', // what type of data do we expect back from the server
        encode          : true,
        success: function(data){
          $('#preview').html(data);
          console.log(data);
        },
        error: function(result) {
          alert("Error " + result);
        }
    });
  });
});
