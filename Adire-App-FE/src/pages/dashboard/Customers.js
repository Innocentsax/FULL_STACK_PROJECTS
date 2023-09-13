import React, { useEffect, useState } from 'react';
import emptyImg from '../../assets/images/emptyDataLogo.svg'
import LoaderLayout from '../../assets/wrappers/LoaderLayout';
import Wrapper from '../../assets/wrappers/Customers';
import { useSelector, useDispatch } from 'react-redux';
import Loading from '../../components/Loading';
import { getAllCustomers } from '../../features/allCustomers/allCustomersSlice';
// import LoadingBtn from '../../components/LoadingBtn';
import { Link } from 'react-router-dom';
import { columns } from "../../utils/gridColumns";
import { Box, useTheme } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
// import Header from "components/Header";
import DataGridCustomToolbar from "../../components/DataGridCustomToolbar";
// import PageBtnContainer from './PageBtnContainer';
// import { changePage } from '../../features/allCustomers/allCustomersSlice';
// import { Table, Pagination } from '@mui/material'


const Customers = () => {
  // values to be sent to the backend
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [sort, setSort] = useState({});
  const [search, setSearch] = useState("");

  const [searchInput, setSearchInput] = useState("");


    const { customers : data, isLoading, totalElements } = useSelector((store) => store.allCustomers);
    const dispatch = useDispatch();
    console.log(data);

    useEffect(() => {
        dispatch(getAllCustomers());
    }, [dispatch]);
  
  if (isLoading) {
    return (
      <LoaderLayout>
        <Loading />
      </LoaderLayout>
    );
  }

  if (data.length === 0) {
    return (
      <Wrapper>
        <div>
          <img src={emptyImg} alt='empty logo' className='empty-img' />
          <p className='oh-no'>oh no!</p>
          <p>You have not created any customer's data yet</p>
          <Link to={"/add-customers"} type='button' className='btn btn-outline'>
              create customer
          </Link>
          {/* <LoadingBtn /> */}
        </div>
      </Wrapper>
    );
  }
console.log(page, search, pageSize);

  return (
    <div>
      <div className='border-btm'>
        <div className='flex-container mg-btm'>
          <h3 className='remove-mg'>Customers</h3>
          <Link to={"/add-customers"} className='btn'>create customer</Link>
        </div>
      </div>

      <Box>
        {/* <Header title="TRANSACTIONS" subtitle="Entire list of transactions" /> */}
        <Box
          height="80vh"
          sx={{
            "& .MuiDataGrid-root": {
              border: "none",
            },
            "& .MuiDataGrid-cell": {
              borderBottom: "1px solid #e5e7eb",
            },
            "& .MuiDataGrid-columnHeaders": {
              backgroundColor: '#F2F4F7',
              color: '#101828',
              borderBottom: "none",
            },
            "& .MuiDataGrid-virtualScroller": {
              backgroundColor: '#ffffff',
            },
            "& .MuiDataGrid-footerContainer": {
              backgroundColor: '#F2F4F7',
              color: '#101828',
              borderTop: "none",
            },
            "& .MuiDataGrid-toolbarContainer .MuiButton-text": {
              color: `#9b7cea !important`,
            },
          }}
        >
          <DataGrid
            columnVisibilityModel={{
              // Hide columns status and traderName, the other columns will remain visible
              id: false,
              // traderName: false,
            }}
            loading={isLoading || !data}
            getRowId={(row) => row.id}
            rows={(data && data) || []}
            columns={columns}
            rowCount={(data && totalElements) || 0}
            rowsPerPageOptions={[10, 20, 50]}
            pagination
            page={page}
            pageSize={pageSize}
            paginationMode="server"
            sortingMode="server"
            onPageChange={(newPage) => setPage(newPage)}
            onPageSizeChange={(newPageSize) => setPageSize(newPageSize)}
            // onSortModelChange={(newSortModel) => setSort(...newSortModel)}
            components={{ Toolbar: DataGridCustomToolbar }}
            componentsProps={{
              toolbar: { searchInput, setSearchInput, setSearch },
            }}
          />
        </Box>
      </Box>

    </div>
  )

    
}


export default Customers