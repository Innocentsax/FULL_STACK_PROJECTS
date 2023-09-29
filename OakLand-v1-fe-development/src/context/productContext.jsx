import { createContext, useCallback, useEffect, useState } from "react";
import axios from 'axios'
import { message } from "antd";
import { errorNotification, successNotification } from "../components/Notification";
import { apiGetAuthorization, apiPut, apiGet, apiPostAuthorization, apiDeleteAuthorization } from "../utils/api/axios";

const ProductsContext = createContext();

const ProductProvider = ({ children }) => {
    const[ordersUrl, setOrdersUrl] = useState("admin/orders")
    const[productUrl, setProductUrl] = useState("products/paginated-all")
    const[favoriteUrl, setFavoriteUrl] = useState("customer/products/favorites/viewAllFavorites")
    const[products, setProducts] = useState([])
    const[pageNumber, setPageNumber] = useState(0)
    const[pageElementSize, setPageElementSize] = useState(0)
    const[totalPages, setTotalPages] = useState(0)
    const[totalElements, setTotalElements] = useState(0)
    const[numOfElements, setNumOfElements] = useState(0)
    const[fetching, setFetching] = useState(true)
    const[singleProduct, setSingleProduct] = useState([])
    const[headerTitle, setHeaderTitle] = useState("Add New Product")

    const[favorites, setFavorites] = useState([])
    const[favPageNumber, setFavPageNumber] = useState(0)
    const[favPageElementSize, setFavPageElementSize] = useState(0)
    const[favTotalPages, setFavTotalPages] = useState(0)
    const[favTotalElements, setFavTotalElements] = useState(0)
    const[favNumOfElements, setFavNumOfElements] = useState(0)


    const[orders, setOrders] = useState([])
    const[orderPageNumber, setOrderPageNumber] = useState(0)
    const[favOrderElementSize, setOrderPageElementSize] = useState(0)
    const[orderTotalPages, setOrderTotalPages] = useState(0)
    const[orderTotalElements, setOrderTotalElements] = useState(0)
    const[orderNumOfElements, setOrderNumOfElements] = useState(0)

    const[productImgUrl, setProductImgUrl] = useState("")

    const getProducts = () => {
        setFetching(true)
        if(productUrl.length > 0) {
            const allProductsUrl = `${productUrl}?pageNo=${pageNumber}`
                apiGet(allProductsUrl)
                .then((res) => {
                    const data = res.data.data
                    setProducts(data.content)
                    setPageNumber(data.number)
                    setPageElementSize(data.size)
                    setTotalPages(data.totalPages)
                    setTotalElements(data.totalElements)
                    setNumOfElements(data.numberOfElements)
                    setFetching(false)
                })
                .catch((err) => {
                    console.log(err)
                    setFetching(false)
                })
        }
    }

    /** DELETE PRODUCT */
    const deleteProduct = (product) => {
        if(product.id !== undefined) {
            console.log(`id: ${product.id}`)
            apiDeleteAuthorization(`admin/products/delete/${product.id}`)
            .then(res => {
              console.log(res);
              message.success(`PRODUCT ${product.name} HAS BEEN DELETED`);
              getProducts();
        
            })
            .catch(err => {
            //   const errorResponse = err.response;
              console.log(err);
            message.error('Product could not be deleted!');
              // message.error(errorResponse);
            })
          }
    }

    /*** EDIT PRODUCT **/
    const editProduct = (onClose, product) => {
        apiPut(`admin/products/update/${singleProduct.id}`, product)
        .then(res => {
            console.log(res)
            onClose()
            getProducts()
        }).catch(err => {
            console.log(err)
        })
    }

    // const fetchSingleProduct = (id) => {
    //     axios.get(`/admin/products/${id}`)
    //     .then(res => {
    //         console.log(res.data)
    //     })
    //     .catch(err => {
    //         console.log(err)
    //     })
    // }

    //********** ADD NEW PRODUCT ********//
    const addNewProduct = (setSubmitting, onClose, newProduct) => {
        setSubmitting(true);
        apiPostAuthorization("admin/products/new", newProduct)
        .then(res => {
            console.log(res.data);
            onClose();
            successNotification("newProduct Successfully Added", `${newProduct.name} was added to the system.`)
            getProducts();
        })
        .catch(err => {
            console.log(err)
            if(err.response.status === 401)
                errorNotification("UnAuthorized", "Contact your admin for access.", "topLeft")
            if(err.response.status >= 500)
                errorNotification("Internal Server Error", "Not connected", "topLeft")
            else errorNotification("No Access", "Contact your admin for access.", "topLeft")
        })
        .finally(() => {
            setSubmitting(false)
        });
    }

    const getFavorites = () => {
        setFetching(true)
        apiGetAuthorization(`${favoriteUrl}?pageNo=${pageNumber}]`)
        .then(res => {
                    const data = res.data.data
                    setProducts(data.content)
                    setPageNumber(data.number)
                    setPageElementSize(data.size)
                    setTotalPages(data.totalPages)
                    setTotalElements(data.totalElements)
                    setNumOfElements(data.numberOfElements)
                    setFetching(false)
        })
        .catch(err => {
            setFetching(false)
            message.error('Click on No');
        })
    }

    const getSingleFavorite = (product) => {
        apiGetAuthorization(`products/favorite/view/${product.id}`)
        .then(res => {
            console.log(res.data)
        })
        .catch(err => {
            console.log(err)
        })
    }


    const addToFavorites = (id) => {
        console.log("Adding to favorites")
        apiPostAuthorization(`customer/products/favorites/add/${id}`)
        .then(res => {
            console.log(res.data)
            message.success("Product added to favorites!")
        })
        .catch(err => {
            message.error(err.response.message);
            console.log(err)
        })
    }
   

   const getFavoritesCallback = useCallback(() => {
        setFetching(true)
        apiGetAuthorization(`${favoriteUrl}?pageNo=${pageNumber}`)
        .then(res => {
                    const data = res.data
                    setFavorites(data.content)
                    setFavPageNumber(data.number)
                    setFavPageElementSize(data.size)
                    setFavTotalPages(data.totalPages)
                    setFavTotalElements(data.totalElements)
                    setFavNumOfElements(data.numberOfElements)
                    setFetching(false)
        })
        .catch(err => {
            setFetching(false)
        })
    }, [pageNumber, favoriteUrl])

    useEffect(() => {
        getFavoritesCallback()
    }, [getFavoritesCallback])


    const getProductsCallback = useCallback(() => {     
        setFetching(true)
        if(productUrl.length > 0) {
            console.log(`ProductUrl: ${productUrl}`)
            const allProductsUrl = `${productUrl}?pageNo=${pageNumber}`
                apiGet(allProductsUrl)
                .then((res) => {
                    const data = res.data.data
                    setProducts(data.content)
                    setPageNumber(data.number)
                    setPageElementSize(data.size)
                    setTotalPages(data.totalPages)
                    setTotalElements(data.totalElements)
                    setNumOfElements(data.numberOfElements)
                    setFetching(false)
                })
                .catch((err) => {
                    console.log(err)
                    setFetching(false)
                })
        }
    }, [productUrl, pageNumber])
    
    useEffect(() => {
        getProductsCallback()
    }, [getProductsCallback])



    const getOrders = () => {
        setFetching(true)
        if(productUrl.length > 0) {
            console.log(`ProductUrl: ${productUrl}`)
            const allProductsUrl = `${productUrl}?pageNo=${pageNumber}`
                apiGet(allProductsUrl)
                .then((res) => {
                    const data = res.data.data
                    setProducts(data.content)
                    setPageNumber(data.number)
                    setPageElementSize(data.size)
                    setTotalPages(data.totalPages)
                    setTotalElements(data.totalElements)
                    setNumOfElements(data.numberOfElements)
                    setFetching(false)
                })
                .catch((err) => {
                    console.log(err)
                    setFetching(false)
                })
        } 
    }


    const getOrdersCallback = useCallback(() => {
        setFetching(true)
        apiGetAuthorization(`${ordersUrl}?pageNo=${pageNumber}`)
        .then(res => {
                console.log(res.data)
                const data = res.data
                setOrders(data.content)
                setOrderPageNumber(data.number)
                setOrderPageElementSize(data.size)
                setOrderTotalPages(data.totalPages)
                setOrderTotalElements(data.totalElements)
                setOrderNumOfElements(data.numberOfElements)
                setFetching(false)
        })
        .catch(err => {
            setFetching(false)
            console.log(err)
        })
    }, [pageNumber, ordersUrl])

    useEffect(() => {
        getOrdersCallback()
    }, [getOrdersCallback])
    

    return( 
        <ProductsContext.Provider value={{ 
            products, 
            singleProduct,
            setProducts, 
            pageElementSize, 
            totalPages, 
            pageNumber, 
            setPageNumber, 
            totalElements,
            numOfElements,
            setProductUrl,

            fetching,
            setFetching,
            getProducts,
            deleteProduct,
            editProduct,
            addNewProduct,
            setSingleProduct,
            setHeaderTitle,
            headerTitle,
            addToFavorites,
            getSingleFavorite,
            getFavorites,
            favoriteUrl,
            setFavoriteUrl,

            favorites,
            setFavorites,
            favPageNumber, 
            setFavPageNumber,
            favPageElementSize, 
            setFavPageElementSize,
            favTotalPages, 
            setFavTotalPages,
            favTotalElements, 
            setFavTotalElements,
            favNumOfElements, 
            setFavNumOfElements,
            productImgUrl,
            setProductImgUrl,
            setOrdersUrl,

            orders, 
            setOrders,
            orderPageNumber, 
            setOrderPageNumber,
            favOrderElementSize, 
            setOrderPageElementSize,
            orderTotalPages, 
            setOrderTotalPages,
            orderTotalElements, 
            setOrderTotalElements,
            orderNumOfElements, 
            setOrderNumOfElements,
            getOrders,

            }}>
            { children }
        </ProductsContext.Provider>
    )
}

export { ProductsContext, ProductProvider };
