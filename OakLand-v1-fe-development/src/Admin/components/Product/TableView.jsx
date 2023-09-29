  import { useState } from 'react'
  import { productColumns } from './productTableData'
  import DrawerForm from '../Forms/DrawerForm'
  import ReactPaginate from 'react-paginate';
  import useProduct from '../../../hooks/useProduct';
  import AddProductForm from '../Forms/AddProductForm'

  import {
    UserAddOutlined,
    LoadingOutlined, 
    CaretLeftOutlined, 
    CaretRightOutlined  } from '@ant-design/icons';
  
  import { Table, Spin, Empty } from 'antd';
import { Star } from '@mui/icons-material';
  const antIcon = <LoadingOutlined style={{ fontSize: 80 }} spin />;

  const TableView = ({ tableTitle }) => {
    const changePage = ({ selected }) => setPageNumber(selected)
    const { products,  totalPages, setPageNumber, fetching, 
      totalElements, setSingleProduct, setHeaderTitle, headerTitle } = useProduct()
    const[showDrawer, setShowDrawer] = useState(false)

    const handleShowDrawer = () => {
      setHeaderTitle("Add New Product")
      setSingleProduct({
              name: "",
              price: "",
              availableQty: "",
              subCategory: "",
              imageUrl: "",
              color: "",
              description: ""
            })
      setShowDrawer(!showDrawer);
    }

    return (
        <section className="table-data-section">
          <DrawerForm
            title={headerTitle}
            showDrawer={showDrawer}
            setShowDrawer={setShowDrawer}
            formLayout={<AddProductForm 
            setShowDrawer={ setShowDrawer } 
            />}
          />
          {fetching &&
            <div style={{ width: "100vw", display: "flex", height:"100%", alignItems: "center", justifyContent: "center", }}>
                <Spin indicator={antIcon} style={{ color: "rgb(218, 196, 161)" }}/>
            </div>
          }

            <div className="table-div">
            {
              products?.length > 0 &&          

              <div className="title-head">
                  <div className='title-sub-head'>
                    <button className="home-btn" onClick={handleShowDrawer}>
                      <UserAddOutlined />Add New Product
                    </button>
                      <button className="btn-count">{totalElements}</button>
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

            {products?.length > 0 && 
              <Table 
              dataSource={ products }
              rowkey={ product => product.name } 
              bordered
              pagination={false}
              scroll={{ x: '400px', y: 600 }}  
              columns={productColumns(setShowDrawer)} 
            />
           }
          </div>
          

          { products?.length === 0 && !fetching && 
              <div style={{ width: "100vw", display: "flex", height:"100%", 
              alignItems: "center", justifyContent: "center", }}>
              <Empty> 
                <button className="home-btn" onClick={handleShowDrawer}>
                  <Star />Add New Product
                </button>
              </Empty>
            </div>
          }   
        </section>
    )
  }

  export default TableView