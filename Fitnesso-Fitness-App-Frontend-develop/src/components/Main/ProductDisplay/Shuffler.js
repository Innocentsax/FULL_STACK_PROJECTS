

const fisherShuffle = (products) => {
    for (var i = products.length - 1; i > 0; i--) {
        var index = Math.floor(Math.random() * i);
        var tmp = products[index];
        products[index] = products[i];
        products[i] = tmp;
        }
        return products;
}

export default fisherShuffle;