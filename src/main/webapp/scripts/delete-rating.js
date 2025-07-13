$(document).on("click", ".open-modal", function() {
    let user_id = $(this).data('userid');
    let movie_id = $(this).data('movieid');
    console.log($(this).data());
    console.log(user_id);
    console.log(movie_id);
    $(".modal-body #userId").val(user_id);
    $(".modal-body #movieId").val(movie_id);
});