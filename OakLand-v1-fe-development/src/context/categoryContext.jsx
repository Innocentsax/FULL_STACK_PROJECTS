import { createContext, useCallback, useEffect, useState } from "react";
import { apiDeleteAuthorization, apiGet, apiPatch, apiPostAuthorization, apiPut } from "../utils/api/axios";

import { errorNotification, 
        successNotification } 
    from "../components/Notification";

export const CategoryContext = createContext()

export const CategoryProvider = ({ children }) => {
    const[categoryUrl, setCategoryUrl] = useState("")
    const[subcategories, setSubcategories] = useState([])
    const[categories, setCategories] = useState([])
    const[totalCategories, setTotalCatgories] = useState(0)
    const [totalSubcategories, setTotalSubcategories] = useState(0)
    const[singleCategory, setSingleCategory] = useState([])
    const[singleSubcategory, setSingleSubcategory] = useState([])
    const[singleSubcategories, setSingleSubcategories] = useState([])


    //PICKUP CENTERS
    const[pickupCenters, setPickCenters] = useState([])
    const[pageNumber, setPageNumber] = useState(0)
    const[pageElementSize, setPageElementSize] = useState(0)
    const[totalPages, setTotalPages] = useState(0)
    const[totalElements, setTotalElements] = useState(0)
    const[numOfElements, setNumOfElements] = useState(0)
    const[fetching, setFetching] = useState(true)
    const[singlePickupCenter, setSinglePickupCenter] = useState([])
    const[pickupCenterSize, setPickupCenterSize] = useState(0)


    //STATES
    const[states, setStates] = useState([])
    const[singleState, setSingleState] = useState([])
    const[totalStates, setTotalStates] = useState(0)


    /***CATEGORY AND SUBCATEGORY */
    useEffect(() => {
        const subCategoriesUrl = `subcategory/view-all`
        apiGet(subCategoriesUrl)
        .then(res => {
            console.log(res.data)
            setSubcategories(res.data)
            setTotalSubcategories(res.data.length)
        }).catch(err => {
            console.log(err)
        })
    }, [])

    useEffect(() => {
        if(categoryUrl){
            apiGet(categoryUrl)
            .then(res => {
              console.log(res.data)
              setSingleSubcategories(res.data)
              setTotalSubcategories(res.data.length)
              
         })
        .catch(err => {
            console.log(err)
            })
        }
      }, [categoryUrl])

    
    const getSubcategories = () => {
        const subCategoriesUrl = `subcategory/view-all`
        apiGet(subCategoriesUrl)
        .then(res => {
            console.log(res.data)
            setSubcategories(res.data)
            setTotalSubcategories(res.data.length)
        }).catch(err => {
            console.log(err)
        })
    }

    const getCategories = () => {
        const categoriesUrl = `category/all`
        apiGet(categoriesUrl)
        .then(res => {
            console.log(res.data)
            setCategories(res.data)
            setTotalCatgories(res.data.length)
        }).catch(err => {
            console.log(err)
        })
    }

    useEffect(() => {
        const categoriesUrl = `category/all`
        apiGet(categoriesUrl)
        .then(res => {
            console.log(res.data)
            setCategories(res.data)
            setTotalCatgories(res.data.length)
        }).catch(err => {
            console.log(err)
        })
    }, [])

    const createSubcategory = (setSubmitting, onClose, data) => {
        setSubmitting(true)
        apiPostAuthorization(`subcategory/admin/new/${singleCategory.id}`, data)
        .then(res => {
            console.log(res.data);
            onClose();
            successNotification("Subcategory Successfully Added", `${data.name} was added to the system.`)
            getCategories();
        })
        .catch(err => {
            console.log(err)
            if(err.response.status === 401)
                errorNotification("UnAuthorized", "Contact your admin for access.", "topLeft")
            if(err.response.status >= 500)
                errorNotification("Internal Server Error", "Not connected", "topLeft")
            else errorNotification("No Access", "An Errr Occurred", "topLeft")
        })
        .finally(() => {
            setSubmitting(false)
        });
    }

    const updateSubcategory = (subcategoryId, updateData) => {
        apiPatch(`subcategory/admin/update/${subcategoryId}`, updateData)
        .then(res => {

        })
        .catch(err => {
            console.log(err)

        })
    }

    const deleteSubcategory = (subcategoryId) => {
        apiDeleteAuthorization(`category/admin/delete/${subcategoryId}`)
        .then(res => {
            successNotification("Subcategory Successfully Deleted", ``)
            getSubcategories()
        })
        .catch(err => {
            console.log(err)
        })
    }

    const createCategory = (setSubmitting, onClose, data) => {
        setSubmitting(true)
        apiPostAuthorization(`category/admin/new`, data)
        .then(res => {
            console.log(res.data);
            onClose();
            successNotification("Category Successfully Added", `${data.name} was added to the system.`)
            getCategories();
        })
        .catch(err => {
            console.log(err)
            if(err.response.status === 401)
                errorNotification("UnAuthorized", "Contact your admin for access.", "topLeft")
            if(err.response.status >= 500)
                errorNotification("Internal Server Error", "Not connected", "topLeft")
            else errorNotification("No Access", "An Errr Occurred", "topLeft")
        })
        .finally(() => {
            setSubmitting(false)
        });
    }

    const updateCategory = (setSubmitting, onClose, updateData) => {
        setSubmitting(true)
        apiPut(`category/admin/update/${singleCategory.id}`, updateData)
        .then(res => {
            onClose()
            successNotification("Category updated!", "Success")
            getCategories()
        })
        .catch(err => {
            console.log(err)

        }).finally(() => setSubmitting(false))
    }

    const deleteCategory = (category) => {
        apiDeleteAuthorization(`category/admin/delete/${category.id}`)
        .then(res => {
            successNotification("Category Successfully Deleted", ``)
            getCategories()
        })
        .catch(err => {
            console.log(err)
        })
    }

    /**=======PICKUP CENTERS=========== */
    const getPickupCenters = () => {
        setFetching(true)
        apiGet(`pickup/all?${pageNumber}`)
        .then(res => {
            const data = res.data
            setPickCenters(data.content)
            setPageNumber(data.number)
            setPageElementSize(data.size)
            setTotalPages(data.totalPages)
            setTotalElements(data.totalElements)
            setPickupCenterSize(data.totalElements)
            setNumOfElements(data.numberOfElements)
            setFetching(false)
        })
        .catch(err => {
            setFetching(false)
            console.log(err)
        })
    }

    const getPickupCentersCallback = useCallback(() => {
        setFetching(true)
        apiGet(`pickup/all?${pageNumber}`)
        .then(res => {
            const data = res.data
            setPickCenters(data.content)
            setPageNumber(data.number)
            setPageElementSize(data.size)
            setTotalPages(data.totalPages)
            setTotalElements(data.totalElements)
            setPickupCenterSize(data.totalElements)
            setNumOfElements(data.numberOfElements)
            setFetching(false)
        })
        .catch(err => {
            console.log("Loading the getPickup callback failed")
            setFetching(false)
            console.log(err)
        })
    }, [pageNumber])

    useEffect(() => {
        getPickupCentersCallback()
    }, [getPickupCentersCallback])


    const createNewPickupCenter = (setSubmitting, onClose, newPickupCenter) => {
        setSubmitting(true);
        apiPostAuthorization("pickup/new", newPickupCenter)
        .then(res => {
            console.log(res.data);
            onClose();
            successNotification("Pickup Center Created", `${newPickupCenter.name} was added to the system.`)
            getPickupCenters();
            setSubmitting(false)
        })
        .catch(err => {
            console.log(err)
            if(err.response.status === 401)
                errorNotification("UnAuthorized", "Contact your admin for access.", "topLeft")
            if(err.response.status >= 500)
                errorNotification("Internal Server Error", "Not connected", "topLeft")
            else errorNotification("No Access", "An Errr Occurred", "topLeft")
        })
        .finally(() => {
            setSubmitting(false)
        });
    }


    const updatePickupCenter = (onClose, newPickupCenter) => {
        apiPut(`pickup/update/${singlePickupCenter.id}`, newPickupCenter)
        .then(res => {
            successNotification("Pickup center updated!", "Success")
            getPickupCenters()
            onClose()
        })
        .catch(err => {
            console.log(err)

        })
    }


    const deletePickupCenter = () => {
        apiPut(`pickup/delete/${singlePickupCenter.id}`)
       .then(res => {
            successNotification("Pickup center deleted!", "Success")
            getPickupCenters()
        })
        .catch(err => {
            console.log(err)

        })
    }


    /*****=============STATE===================*****/
     const createNewState = (setSubmitting, onClose, newState) => {
        setSubmitting(true);
        console.log("Received input: " + newState.nameOfState)
        apiPostAuthorization("state/admin/create_state", newState)
        .then(res => {
            console.log(res.data);
            onClose();
            successNotification("Successful", `${newState.nameOfState} was added to the system.`)
            getAllStates();
        })
        .catch(err => {
            console.log(err)
            console.log(err.status)
            if(err.response.status === 401)
                errorNotification("UnAuthorized", "Contact your admin for access.", "topLeft")
            if(err.response.status >= 500)
                errorNotification("Internal Server Error", "Not connected", "topLeft")
            else errorNotification("Error", err.response.message, "topLeft")
        })
        .finally(() => {
            setSubmitting(false)
        });
    }

    const deleteState = (stateId) => {
        apiDeleteAuthorization(`state/admin/delete_state/${stateId}`)
        .then(res => {
            console.log(res.data)
            getAllStates()
        })
        .catch(err => {
            console.log(err)
        })
    }

    const getAllStates = () => {
        apiGet(`state/view-states`)
        .then(res => {
            console.log(res)
            setStates(res.data)
            setTotalStates(res.data.length)
        })
        .catch(err => {
            console.log(err)
        })
    }

    const getAllStatesCallback = useCallback(() => {
        apiGet(`state/view-states`)
        .then(res => {
            console.log(res)
            setStates(res.data)
            setTotalStates(res.data.length)
        })
        .catch(err => {
            console.log(err)
        })
    }, [])

    useEffect(() => {
        getAllStatesCallback()
    }, [getAllStatesCallback])

    const updateState = () => {
        console.log("Updating state")
    }


    return <CategoryContext.Provider value={{ 
        subcategories, 
        categories,
        getSubcategories,
        setCategoryUrl,
        setSingleSubcategories,
        singleSubcategories,
        setSubcategories,
        singlePickupCenter,
        setSinglePickupCenter,
        pageElementSize, 
        totalPages, 
        pageNumber, 
        setPageNumber, 
        totalElements,
        numOfElements,
        fetching,
        pickupCenters,
        getPickupCenters,
        createNewPickupCenter,
        updatePickupCenter,
        deletePickupCenter,
        states,
        singleState,
        setSingleState,
        createNewState,
        getAllStates,
        deleteState,
        updateState,
        totalStates,
        singleCategory,
        setSingleCategory,
        singleSubcategory,
        setSingleSubcategory,
        totalCategories,
        totalSubcategories,

        createSubcategory,
        createCategory,
        updateCategory,
        updateSubcategory,
        deleteCategory,
        deleteSubcategory,
        pickupCenterSize,

        }}>
        { children }
    </CategoryContext.Provider>
}