// import { DotIcon } from './SVGIcons'
import { Link } from 'react-router-dom';
import ActionMenu from '../components/ActionMenu'
import OrderActionMenu from '../components/OrderActionMenu'
import moment from 'moment'
import { statusOptions } from './enumData';


export const columns = [
    {
        field: "id",
        headerName: "ID",
        flex: 1,
    },
    {
      field: "name",
      headerName: "Name of customer",
      flex: 1,
      renderCell: ({ row }) => {
        return (
          `${row.firstName} ${row.lastname}`
        )
      }
    },
    {
      field: "email",
      headerName: "Email",
      flex: 1,
    },
    {
      field: "phoneNumber",
      headerName: "Phone number",
      flex: 1,
    },
    {
      field: "address",
      headerName: "Address",
      flex: 0.5,
    //   sortable: false,
    //   renderCell: (params) => params.value.length,
    },
    {
      field: "action",
      headerName: "Action",
      flex: 1,
      align: "center",
    //   renderCell: (params) => `$${Number(params.value).toFixed(2)}`,
      // renderCell: ({ row }) => {
      renderCell: ({ row }) => {
        console.log({...row});
        return (
          <ActionMenu {...row} />
          // <ActionMenu id={row.id} />
        )
      }
    },
  ];

  const columnss = [
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







  export const orderColumns = [
    {
        field: "id",
        headerName: "ID",
        flex: 1,
    },
    {
      field: "customerEmail",
      headerName: "Customer's email",
      flex: 1,
    },
    {
      field: "duration",
      headerName: "Duration",
      flex: 0.6,
      renderCell: ({ row }) => {
        return (
          `${row.duration} ${row.duration > 1 ? 'days' : 'day'}`
        )
      }
    },
    {
      field: "dueDate",
      headerName: "Due Date",
      flex: 1,
      renderCell: ({ row }) => moment(row.dueDate).format('MMM Do, YYYY'),
      // sortable: false,
    //   renderCell: (params) => params.value.length,
    },
    {
      field: "orderPrice",
      headerName: "Price",
      flex: 0.6,
      // renderCell: (params) => `$${Number(params.orderPrice).toFixed(2)}`,
    },
    {
      field: "status",
      headerName: "Status",
      flex: 0.7,
      renderCell: ({ row }) => {
        console.log(statusOptions[row.orderStatus]);
        return (
          <p 
            className={`${statusOptions[row.orderStatus].toLowerCase().includes('progress') ? 'progress' : statusOptions[row.orderStatus].toLowerCase()}`}
          >
            {statusOptions[row.orderStatus]}
          </p>
        )
      }
    },
    {
      field: "action",
      headerName: "Action",
      flex: 0.5,
      align: "center",
    //   renderCell: (params) => `$${Number(params.value).toFixed(2)}`,
      // renderCell: (params) => <ActionMenu {...{ params }} />
      renderCell: ({ row }) => {
        console.log({...row});
        return (
          <OrderActionMenu {...row} />
        )
      }
    },
    {
      field: "receipt",
      headerName: "Receipt",
      flex: 1,
      align: "center",
    //   renderCell: (params) => `$${Number(params.value).toFixed(2)}`,
      renderCell: (params) => {
        return (
          <Link 
            className='generate-receipt-btn' 
            to={`/orders-receipt/${params.row.id}`}
           
          >
            Generate receipt
          </Link>
        )
      }
    },
  ];

