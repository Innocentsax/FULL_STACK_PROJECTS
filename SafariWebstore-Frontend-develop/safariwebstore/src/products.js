import axios from "axios"

const products = productList()

function productList() {
    axios.get("http://localhost:8080/api/products/?pageNo=0&pageSize=20").then(res => {
     return res.data
    })
}

// const products = [
//     {
//         _id: '1',
//         productName:'2020 hot new women shoes PU',
//         image: 'products/2020-hot-new-women-shoes-PU-sequined.png',
//         price: 23000 ,
//     },
//     {
//         _id: '2',
//         productName:'Cigarette-trousers',
//         image: 'products/Cigarette-trousers.png',
//         price: 43000,
//     },
//     {
//         _id: '3',
//         productName:'Diamond-Ladies-Ring',
//         image: 'products/Diamond-Ladies-Ring.png',
//         price: 2500,
//     },
//     {
//         _id: '4',
//         productName:'Engin-akyurt',
//         image: 'products/Engin-akyurt.png',
//         price: 4500,
//     },
//     {
//         _id: '5',
//         productName:'Grey-Bee-Watch',
//         image: 'products/Grey-Bee-Watch.png',
//         price: 5440
//     },
//     {
//         _id: '6',
//         productName:'engin-akyurt-TDOCini',
//         image: 'products/engin-akyurt-TDOCini.png',
//         price: 3000,
//     },
//     {
//         _id: '7',
//         productName:'Guilhermina-Embellished',
//         image: 'products/Guilhermina-Embellished.png',
//         price: 4500,
//     },
//     {
//         _id: '8',
//         productName:'gustavo-spindula',
//         image: 'products/gustavo-spindula.png',
//         price: 5600,
//     },
//     {
//         _id: '9',
//         productName:'Kyla-Tambourine-Crossbody-Bag',
//         image: 'products/Kyla-Tambourine-Crossbody-Bag.png',
//         price: 6700
//     },
//     {
//         _id: '10',
//         productName:'Nereide-Oval1',
//         image: 'products/Nereide-Oval1.png',
//         price: 6700
//     },
//     {
//         _id: '11',
//         productName:'SupergaPlaid-Sneakers',
//         image: 'products/SupergaPlaid-Sneakers.png',
//         price: 6700
//     },
//     {
//         _id: '3',
//         productName:'Diamond-Ladies-Ring',
//         image: 'products/Diamond-Ladies-Ring.png',
//         price: 2500,
//     },
// ]

export default products;