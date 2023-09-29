import { createContext, useCallback, useEffect, useState } from "react"
import { apiGetAuthorization } from "../utils/api/axios"

export const PersonContext = createContext()

export const PersonProvider = ({ children }) => {
    const[persons, setPersons] = useState([])
    const[pageNumber, setPersonPageNumber] = useState(0)
    const[personPageElementSize, setPersonPageElementSize] = useState(0)
    const[personTotalPages, setPersonTotalPages] = useState(0)
    const[personTotalElements, setPersonTotalElements] = useState(0)
    const[personNumOfElements, setPersonNumOfElements] = useState(0)
    const[personFetching, setPersonFetching] = useState(true)
    
    const getCustomers = () => {
        apiGetAuthorization(`admin/customers-profile/page-sort?pageNo=${pageNumber}`)
        .then(res => {
                const data = res.data.data
                setPersons(data.content)
                setPersonPageNumber(data.number)
                setPersonPageElementSize(data.size)
                setPersonTotalPages(data.totalPages)
                setPersonTotalElements(data.totalElements)
                setPersonNumOfElements(data.numberOfElements)
                setPersonFetching(false)
        })
        .catch(err => {
            console.log(err)
        })
    }

    const getPersonsCallback = useCallback(() => {
            apiGetAuthorization(`admin/customers-profile/page-sort?pageNo=${pageNumber}`)
            .then(res => {
                    const data = res.data.data
                    setPersons(data.content)
                    setPersonPageNumber(data.number)
                    setPersonPageElementSize(data.size)
                    setPersonTotalPages(data.totalPages)
                    setPersonTotalElements(data.totalElements)
                    setPersonNumOfElements(data.numberOfElements)
                    setPersonFetching(false)
            })
            .catch(err => {
                console.log(err)
            })
    }, [pageNumber])


    // const deactivatePerson = (person) => {
    //     axios.put(`/deactivate-user/${person.id}`)
    //     .then(res => {

    //     })
    //     .catch(err => {
            
    //     })

    // }


    useEffect(() => {
        getPersonsCallback()
    }, [getPersonsCallback])

    return (
        <PersonContext.Provider value={{ 
                persons, 
                setPersons,
                setPersonPageNumber,
                personPageElementSize,
                personTotalPages,
                personTotalElements,
                personNumOfElements,
                personFetching,
                setPersonFetching
            }}>
            {children}
        </PersonContext.Provider>
    )
}