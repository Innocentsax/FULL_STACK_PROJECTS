const config = {
    headers: {
      Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyVHlwZSI6IkJPUlJPV0VSIiwidXNlcklkIjoiMTliZjM3MzQtZGQ3ZC00MGU3LWIxYzgtYWM4YjJlOTdlODRmIiwic3ViIjoiY2hpb3JsdWphY2tAZ21haWwuY29tIiwiaWF0IjoxNjkxODYxNzE3LCJleHAiOjE3NzMwNzEzMTd9.97A9nmlW7EYgaWAwV-K98a95qfeMoid5MJ2oFmBwiMs`,
      'Content-Type': 'application/json', 
    },
  };


fetch("http://localhost:8083/api/profile/get-profile",config)
.then((res)=>{
    return res.json()
}).then((data)=>{
    console.log(data)
})