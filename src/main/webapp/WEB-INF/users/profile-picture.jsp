<div class="container py-5">
    <div class="row border rounded">
        <div class="m-3">
            <form action="profile-picture" method="post" enctype="multipart/form-data">
                <div class="m-3 d-flex align-items-center justify-content-center">
                    <c:choose>
                        <c:when test="${empty image.encodedImage}">
                            <img class="cardImage mx-3" src="${appURL}/img/profile.svg" alt="Unset Profile Picture" height="125" width="125">
                        </c:when>
                        <c:otherwise>
                            <img class="cardImage mx-3" src="data:image/jpeg;base64,${image.encodedImage}" alt="${image.fileName}" height="125" width="125">
                        </c:otherwise>
                    </c:choose>

                    <div>
                        <input type="file" name="imageFile" id="imageFile" accept="image/jpeg, image/jpg"/>
                        <br/>
                        <label for="imageFile" class="form-floating text-secondary"><i>.jpg / .jpeg only</i></label>
                    </div>
                </div>
                <br/>
                <div class="d-flex justify-content-center">
                    <button type="submit" class="btn btn-primary">Choose New Image</button>
                </div>
            </form>
        </div>
    </div>
</div>