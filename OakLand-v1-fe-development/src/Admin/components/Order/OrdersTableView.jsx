import { useState } from 'react'
import { productColumns } from './ordersTableData'
import DrawerForm from '../Forms/DrawerForm'
import ReactPaginate from 'react-paginate';
import AddProductForm from '../Forms/AddProductForm'
import CreditCardIcon from "@mui/icons-material/CreditCard";

import {
  UserAddOutlined,
  LoadingOutlined,
  CaretLeftOutlined, 
  CaretRightOutlined
} from '@ant-design/icons';

import { Table, Spin, Empty } from 'antd';
import useProduct from '../../../hooks/useProduct';
const antIcon = <LoadingOutlined style={{ fontSize: 80 }} spin />;

  const OrderTableView = ({ tableTitle }) => {
    const { orders,  totalPages, setPageNumber, fetching, 
            orderTotalElements, 
            setHeaderTitle, headerTitle } = useProduct()

    const changePage = ({ selected }) => setPageNumber(selected)
    const[showDrawer, setShowDrawer] = useState(false)

    return (
        <section className="table-data-section">
          {fetching && 
            <div style={{ width: "100vw", display: "flex", height:"100%", alignItems: "center", justifyContent: "center", }}>
                <Spin indicator={antIcon} style={{ color: "rgb(218, 196, 161)" }}/>
            </div>
          }

          <div className="table-div">
            {
              orders?.length > 0 &&         

            <div className="title-head">
                <div className='title-sub-head'>
                    <button className="btn-count">{orderTotalElements}</button>
                </div>
                <h2 className='"layout-h2-header'>{tableTitle}</h2>
              <ReactPaginate 
                previousLabel={<CaretLeftOutlined />}
                nextLabel={<CaretRightOutlined />}
                pageCount={totalPages} 
                onPageChange={changePage}
                containerClassName={"paginationBtns"}
                previousLinkClassName={"prevBtn"}
                nextLinkClassName={"nextBtn"}
                disabledClassName={"paginationDisabled"}
                activeClassName={"paginationActive"}
              />
            </div> 
          }

          { orders?.length > 0 && 
            <Table 
            dataSource={ orders }
            rowkey={ order => order.id } 
            bordered
            pagination={false}
            scroll={{ x: '400px', y: 600 }}  
            columns={productColumns}                
           />
          }
          </div>
          

          { orders?.length === 0 && !fetching && 
            <div style={{ width: "100vw", display: "flex", height:"100%", 
              alignItems: "center", justifyContent: "center", }}>
              <Empty> 
                <button className="home-btn" >
                {/* onClick={handleShowDrawer}> */}
                  <CreditCardIcon />You have no orders
                </button>
              </Empty>
            </div>
          }  
        </section>
    )
  }

  export default OrderTableView