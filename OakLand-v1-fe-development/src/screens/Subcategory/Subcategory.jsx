import './favorites.css'
import { DownOutlined } from '@ant-design/icons';
import { Dropdown, Space } from 'antd'
import { useParams } from 'react-router-dom';
import { useEffect } from 'react';
import Categories from '../../components/CategoryCard/Categories';
import useCategory from '../../hooks/useCategory';
import CategoryItem from '../../components/CategoryCard/CategoryItem';
import { Link } from 'react-router-dom'

const items = [
    {
      label: 'Sort By Name',
      key: '1',
    },
    {
      label: 'Sort By Image',
      key: '2',
    }, 
  ];

const Subcategory = ({ url, title }) => {
    const params = useParams()
    const{ 
        setCategoryUrl, 
        singleSubcategories,
        setSingleSubcategories
    } = useCategory()

    const onClick = ({ key }) => {
        switch(key) {
            case '1':
                setSingleSubcategories([...singleSubcategories.sort((a, b) => (a.name > b.name) ? 1 : -1)])
                return
          case '2':
                setSingleSubcategories([...singleSubcategories.sort((a, b) => (a.imageUrl > b.imageUrl) ? 1 : -1)])
                return
            default: 
                return singleSubcategories
        }
    };

    useEffect(() => {
      setCategoryUrl(`${url}/${params.id}`)
    }, [params, url, setCategoryUrl])

    return (
      <section className="favorites-section">
          <div className="products-info">
               { singleSubcategories.length <= 16 ? 
                <p>Showing all { singleSubcategories?.length } subcategories </p> : <p></p> }

                {/* {
                numOfElements < pageElementSize ?
                <p>Showing { (pageNumber * pageElementSize) + 1 } - { totalElements } of { totalElements }</p> :
                <p>Showing { numOfElements * (pageNumber) + 1 } - { numOfElements * (pageNumber + 1) } of {totalElements} results</p>
              } */}
              <h1>{ title }</h1>
              <Dropdown menu={{ items, onClick,}}>
                  <a onClick={(e) => e.preventDefault()} href={"sorting"} id="dropdown-link">
                  <Space>
                      Default Sorting
                      <DownOutlined />
                  </Space>
                  </a>
              </Dropdown>
          </div>
          
          <div className="encompassing-div">
            <Categories displayCategories={true} />
            <div className="this-product-container">
            <div className="favorites-div">
                {
                  singleSubcategories?.map((category, index) => 
                    <Link to={`/categories/subcategories/${category.id}/shop`} key={ index }>
                      <CategoryItem category={ category }/>
                    </Link>
                  )
                }
                </div>
        </div>
        </div>
      </section>
    )
}

export default Subcategory