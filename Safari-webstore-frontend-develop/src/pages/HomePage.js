import { React, useState, useEffect} from 'react';
// import '../../src/App.css';   
import Hero  from '../components/Hero';
import CardHomeProducts from '../components/CardHomeProducts'
import Pagination from '../components/Pagination';
import Footer from '../components/Footer';
import ProductApi from '../apis/ProductApi';


function HomePage() {
    
    const [products, setProducts] = useState([]);

    useEffect(() => {
        (async () => {
        let mounted = true;

        const allProducts = await ProductApi.getAllProducts();

        function shuffleArray(array) {
            let i = array.length - 1;
            for (; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            const temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            }
            return array;
        }

        if(mounted) setProducts(shuffleArray(allProducts.content));

        return () => mounted = false;

        })();
    }, [])

    const [currentPage, setCurrentPage] = useState(1);
    const [productsPerPage] = useState(16);
    const indexOfLastProducts = currentPage * productsPerPage;
    const indexOfFirstProducts = indexOfLastProducts - productsPerPage;
    const currentProducts = products.slice(indexOfFirstProducts, indexOfLastProducts);

    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    return (
        <>
        <Hero />
        <CardHomeProducts products={currentProducts}/>
        <Pagination itemsPerPage = {productsPerPage} totalPages={products.length} paginate={paginate} currentPage={currentPage} />
        <Footer />
        </>
    )
}


export default HomePage;