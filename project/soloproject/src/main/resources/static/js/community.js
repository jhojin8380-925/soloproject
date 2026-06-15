let categoryLink = document.querySelectorAll(".category-link");
let categoryLi = document.querySelectorAll(".category-li")

const url = location.search;

const category = url.trim().split("category=")[1];
console.log(category);

addEventListener('load', function() {
    if (category == null) {
        categoryLink[0].style.backgroundColor = "black";
        categoryLink[0].style.color = "white";
        categoryLink[0].style.border = "0px";
        categoryLink[0].style.fontWeight = "bold";
    } else if (category == "all") {
        categoryLink[0].style.backgroundColor = "black";
        categoryLink[0].style.color = "white";
        categoryLink[0].style.border = "0px";
        categoryLink[0].style.fontWeight = "bold";
    } else if (category == "new") {
        categoryLink[1].style.backgroundColor = "black";
        categoryLink[1].style.color = "white";
        categoryLink[1].style.border = "0px";
        categoryLink[1].style.fontWeight = "bold";
    } else if (category == "free") {
        categoryLink[2].style.backgroundColor = "black";
        categoryLink[2].style.color = "white";
        categoryLink[2].style.border = "0px";
        categoryLink[2].style.fontWeight = "bold";
    } else if (category == "question") {
        categoryLink[3].style.backgroundColor = "black";
        categoryLink[3].style.color = "white";
        categoryLink[3].style.border = "0px";
        categoryLink[3].style.fontWeight = "bold";
    }
})


/*addEventListener('load', function(){
    categoryLink[0].style.backgroundColor = "black";
    categoryLink[0].style.color = "white";
    categoryLink[0].style.border = "0px";
    categoryLink[0].style.fontWeight = "bold";
})*/


/*for(let i = 0; i < categoryLink.length; i++){
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
} */


