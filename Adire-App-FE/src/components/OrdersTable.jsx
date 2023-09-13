import React, { useEffect, useState } from 'react';
import emptyImg from '../assets/images/emptyDataLogo.svg'
import Wrapper from '../assets/wrappers/Customers';
import LoaderLayout from '../assets/wrappers/LoaderLayout';
import { useSelector, useDispatch } from 'react-redux';
import Loading from '../components/Loading';
import LoadingBtn from '../components/LoadingBtn';
import { Link } from 'react-router-dom';
import { orderColumns } from "../utils/gridColumns";
import { Box, useTheme } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
// import Header from "components/Header";
import DataGridCustomToolbar from "../components/DataGridCustomToolbar";
// import { mockOrderData as data } from "../utils/data";
// import PageBtnContainer from './PageBtnContainer';
// import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';
import { getAllOrders } from '../features/allOrders/allOrdersSlice'
import OrdersWrapper from '../assets/wrappers/OrdersWrapper';


const OrdersTable = () => {
    
  // values to be sent to the backend
  const [page, setPage] = useState(1);
  const [pageSize, setPageSize] = useState(20);
  const [sort, setSort] = useState({});
  const [search, setSearch] = useState("");

  const [searchInput, setSearchInput] = useState("");


    const { orders  : data, isLoading, totalElements } = useSelector((store) => store.allOrders);
    const dispatch = useDispatch();
    console.log(data, orderColumns);

    // const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const handleChange = (event, value) => {
    setPage(value);
  };

    const columns = [
        { id: 'name', label: 'Name', minWidth: 170 },
        { id: 'code', label: 'ISO\u00a0Code', minWidth: 100 },
        {
          id: 'population',
          label: 'Population',
          minWidth: 170,
          align: 'right',
          format: (value) => value.toLocaleString('en-US'),
        },
        {
          id: 'size',
          label: 'Size\u00a0(km\u00b2)',
          minWidth: 170,
          align: 'right',
          format: (value) => value.toLocaleString('en-US'),
        },
        {
          id: 'density',
          label: 'Density',
          minWidth: 170,
          align: 'right',
          format: (value) => value.toFixed(2),
        },
      ];

    function createData(name, code, population, size) {
        const density = population / size;
        return { name, code, population, size, density };
      }
      
      const rows = [
        createData('India', 'IN', 1324171354, 3287263),
        createData('China', 'CN', 1403500365, 9596961),
        createData('Italy', 'IT', 60483973, 301340),
        createData('United States', 'US', 327167434, 9833520),
        createData('Canada', 'CA', 37602103, 9984670),
        createData('Australia', 'AU', 25475400, 7692024),
        createData('Germany', 'DE', 83019200, 357578),
        createData('Ireland', 'IE', 4857000, 70273),
        createData('Mexico', 'MX', 126577691, 1972550),
        createData('Japan', 'JP', 126317000, 377973),
        createData('France', 'FR', 67022000, 640679),
        createData('United Kingdom', 'GB', 67545757, 242495),
        createData('Russia', 'RU', 146793744, 17098246),
        createData('Nigeria', 'NG', 200962417, 923768),
        createData('Brazil', 'BR', 210147125, 8515767),
      ];
      

    useEffect(() => {
        dispatch(getAllOrders());
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
            <p>You have not orders yet, come back</p>
            <Link to={"/orders"} type='button' className='btn btn-outline'>
                create order
            </Link>
            {/* <LoadingBtn /> */}
            </div>
        </Wrapper>
        );
    }


    return (
        <OrdersWrapper>
            <div className='border-btm'>
                <div className='flex-container mg-btm'>
                <h3 className='remove-mg'>Orders</h3>
                <Link to={"/orders"} className='btn'>create order</Link>
                </div>
            </div>

            <Box>
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
                    }}
                    loading={isLoading || !data}
                    getRowId={(row) => row.id}
                    rows={data || []}
                    columns={orderColumns}
                    rowCount={(data && data.totalElements) || 0}
                    rowsPerPageOptions={[5, 10, 20]}
                    pagination
                    page={page}
                    pageSize={pageSize}
                    paginationMode="server"
                    sortingMode="server"
                    onPageChange={(newPage) => setPage(newPage)}
                    onPageSizeChange={(newPageSize) => setPageSize(newPageSize)}
                    onSortModelChange={(newSortModel) => setSort(...newSortModel)}
                    components={{ Toolbar: DataGridCustomToolbar }}
                    componentsProps={{
                    toolbar: { searchInput, setSearchInput, setSearch },
                    }}
                    />
                </Box>
            </Box>

            {/* <Paper sx={{ width: '100%', overflow: 'hidden', height: 500 }}>
                <TableContainer sx={{ maxHeight: 500 }}>
                    <Table stickyHeader aria-label="sticky table">
                    <TableHead>
                        <TableRow>
                        {columns.map((column) => (
                            <TableCell
                            key={column.id}
                            align={column.align}
                            style={{ minWidth: column.minWidth }}
                            >
                            {column.label}
                            </TableCell>
                        ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rows
                        .slice((page - 1) * rowsPerPage, (page - 1) * rowsPerPage + rowsPerPage)
                        .map((row) => {
                            return (
                            <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                                {columns.map((column) => {
                                const value = row[column.id];
                                return (
                                    <TableCell key={column.id} align={column.align}>
                                    {column.format && typeof value === 'number'
                                        ? column.format(value)
                                        : value}
                                    </TableCell>
                                );
                                })}
                            </TableRow>
                            );
                        })}
                    </TableBody>
                    </Table>
                </TableContainer>
            </Paper>
            <Stack spacing={2}>
            <Pagination count={5} page={page} onChange={handleChange} />
            </Stack> */}
        </OrdersWrapper>
    )
    
}


export default OrdersTable