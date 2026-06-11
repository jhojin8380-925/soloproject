let categoryLink = document.querySelectorAll(".category-link");
let categoryLi = document.querySelectorAll(".category-li")

/*addEventListener('load', function(){
    categoryLink[0].style.backgroundColor = "black";
    categoryLink[0].style.color = "white";
    categoryLink[0].style.border = "0px";
    categoryLink[0].style.fontWeight = "bold";
})*/


for(let i = 0; i < categoryLink.length; i++){
    categoryLink[i].addEventListener('click', function(){
        for(let i = 0; i < categoryLink.length; i++){
            categoryLink[i].style.backgroundColor = "white";
            categoryLink[i].style.color = "black";
            categoryLink[i].style.border = "2px solid #dddddd";
            categoryLink[i].style.fontWeight = "lighter";
        }
        categoryLink[i].style.backgroundColor = "black";
        categoryLink[i].style.color = "white";
        categoryLink[i].style.border = "0px";
        categoryLink[i].style.fontWeight = "bold";

    })
}


