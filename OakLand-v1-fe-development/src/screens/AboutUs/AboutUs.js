import React, { Fragment, useState } from "react";
import leather from "./image/leather-sofa.jpg"
import "./aboutUs.css";

const AboutUs = () => {
  const [toggleTab, setToggleTab] = useState(1);
  const toggleState = (index) => {
    setToggleTab(index);
  };
  return (
    <Fragment>
      <section className="about">
        <div className="row">
          <div className="column1">
            <img
              src={leather}
              alt="about-img"
            />
          </div>
          <div className="column">
            <div className="tabs">
              <div
                className={
                  toggleTab === 1 ? "single-tab active-tab" : "single-tab"
                }
                onClick={() => toggleState(1)}
              >
                <h2 className="aisha">About</h2>
              </div>
              <div
                className={
                  toggleTab === 2 ? "single-tab active-tab" : "single-tab"
                }
                onClick={() => toggleState(2)}
              >
                <h2 className="aisha">Skill</h2>
              </div>

              <div
                className={
                  toggleTab === 3 ? "single-tab active-tab" : "single-tab"
                }
                onClick={() => toggleState(3)}
              >
                <h2 className="aisha">Experience</h2>
              </div>
            </div>
            <div className="tab-content">
              {/* About content */}
              <div
                className={
                  toggleTab === 1 ? "content active-content" : "content"
                }
              >
                <h2>Our Story</h2>
                <p>
                  {" "}
                  Oakland Furniture Store is a Nigerian-owned and operated
                  business that has been serving the community for over 25
                  years. We pride ourselves on offering a wide selection of
                  high-quality furniture at affordable prices. Our showroom
                  features a variety of styles, from traditional to
                  contemporary, and we have something for every room in the
                  house. Our knowledgeable and friendly staff are always
                  available to help you find the perfect piece for your home.
                  Whether you're looking for a new living room set, a dining
                  table, or a bedroom suite, we have something to suit your
                  needs and budget. We also offer custom ordering and furniture
                  design services, so if you have a specific vision in mind, we
                  can help bring it to life. At Oakland Furniture Store, we
                  believe that everyone deserves to have a beautiful and
                  comfortable home. That's why we offer financing options and
                  free delivery and installation on qualifying purchases. We are
                  committed to providing our customers with the best possible
                  service and we look forward to helping you create the home of
                  your dreams.
                </p>
                <h3>OakLand Furnitures Your Imagination at your doorstep</h3>
                <p>
                  At Oakland Furniture, we believe that your home should be a
                  reflection of your unique style and personality. That's why we
                  go to great lengths to find the best furniture from all around
                  the world to offer our customers. We pride ourselves on being
                  experts in furniture sourcing and importing, and we are
                  constantly on the lookout for the latest trends and designs in
                  furniture. From the sleek and modern styles of Europe to the
                  traditional and handcrafted pieces of Asia, we have something
                  for every taste and budget. Our showroom features a wide
                  selection of imported furniture, including dining tables,
                  sofas, chairs, and bedroom sets. We also offer a variety of
                  custom ordering and furniture design services, so if you have
                  a specific vision in mind, we can help bring it to life. We
                  believe in offering our customers the best possible quality,
                  and that's why we only work with reputable manufacturers and
                  suppliers who share our commitment to craftsmanship and
                  durability. We also ensure that all of our imported furniture
                  meets all necessary safety and environmental standards. At
                  Oakland Furniture, we are passionate about bringing you the
                  best furniture from around the world, and we look forward to
                  helping you create a home that is truly one-of-a-kind.
                </p>
              </div>
              {/* Skills Content*/}
              <div
                className={
                  toggleTab === 2 ? "content active-content" : "content"
                }
              >
                <h2>Our vision & mission</h2>
                <p>
                  {" "}
                  <h4>Vision:</h4>
                  At OakLand Furniture, our vision is to be the leading provider
                  of high-quality and unique furniture for homes and businesses.
                  We strive to be the go-to destination for customers seeking to
                  create a beautiful and comfortable living space that reflects
                  their individual style and personality.
                  <h4>Mission:</h4>
                  Our mission is to provide our customers with an unparalleled
                  selection of furniture sourced from all around the world, at
                  competitive prices. We are committed to offering exceptional
                  customer service, and our knowledgeable and friendly staff are
                  always available to help customers find the perfect piece for
                  their home or office. We also provide custom ordering and
                  furniture design services to help our customers bring their
                  vision to life. Additionally, we are dedicated to ensuring
                  that our furniture meet the necessary safety and environmental
                  standards. We believe that everyone deserves to have a
                  beautiful home, and we are passionate about helping our
                  customers create the home of their dreams.
                </p>
                <div className="skills-row">
                  <div className="skills-colum">
                    <div className="progress-wrap">
                      <h3>Quality</h3>
                      <div className="progress">
                        <div className="progress-bar">
                          <span>100%</span>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="skills-colum">
                    <div className="progress-wrap">
                      <h3>Uniqueness</h3>
                      <div className="progress">
                        <div className="progress-bar">
                          <span>100%</span>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="skills-colum">
                    <div className="progress-wrap">
                      <h3>Reliabity</h3>
                      <div className="progress">
                        <div className="progress-bar sql">
                          <span>100%</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              {/* Experience */}
              <div
                className={
                  toggleTab === 3 ? "content active-content" : "content"
                }
              >
                <div className="exp-column">
                  <h3>
                    Glass and Plastic furniture, also known as acrylic furniture
                  </h3>

                  <p>
                    At Oakland Furniture, we have honed our skills in providing
                    high-quality glass and plastic furniture to our customers.
                    We have a deep understanding of the unique properties and
                    characteristics of these materials and use this knowledge to
                    carefully curate the best pieces for our customers. Our
                    showroom features a wide variety of glass and plastic
                    furniture options for every room in the house, from modern
                    and contemporary styles to more traditional designs. We
                    offer an extensive selection of glass and plastic furniture
                    for both indoor and outdoor use, from elegant glass dining
                    tables and stylish clear acrylic coffee tables to durable
                    plastic outdoor furniture and accent pieces. Additionally,
                    we provide custom ordering and furniture design services,
                    allowing customers to create unique pieces that perfectly
                    match their individual style. With our expertise and
                    knowledge of glass and plastic furniture, we are confident
                    that we can help our customers find the perfect piece for
                    their home or office.
                  </p>
                </div>
                <div className="exp-column">
                  <h3>Bamboo, Wooden and Wicker or rattan furniture</h3>

                  <p>
                    Oakland Furniture is an experienced provider of high-quality
                    wooden, bamboo and wicker furniture. With over 25 years of
                    experience in the industry, we have a deep understanding of
                    the unique characteristics and qualities of these materials,
                    and we use this knowledge to handpick the best pieces for
                    our customers. Our showroom features a wide selection of
                    wooden, bamboo and wicker furniture for every room in the
                    house, from traditional to contemporary styles. From sturdy
                    and durable wooden dining tables, to elegant and natural
                    bamboo bedroom sets, and from charming wicker patio
                    furniture to modern bamboo living room sofas, we have
                    something to suit every taste and budget. We are also
                    experts in custom ordering and furniture design and can help
                    customers create unique pieces that perfectly match their
                    individual style.
                  </p>
                </div>
                <div className="exp-column">
                  <h3>Metal and Concrete Furniture</h3>
                  <p>
                    Oakland Furniture is a well-versed provider of high-quality
                    metal and concrete furniture. Our years of experience in the
                    industry has given us a deep understanding of the unique
                    properties and characteristics of these materials, and we
                    use this knowledge to carefully select the best pieces for
                    our customers. Our showroom showcases a wide variety of
                    metal and concrete furniture for every room in the home,
                    from modern to rustic styles. From durable metal dining
                    tables and sturdy concrete coffee tables to stylish concrete
                    living room accent pieces and elegant metal bed frames, we
                    have something that will fit every taste and budget.
                    Additionally, we are experts in custom ordering and
                    furniture design and can help customers create one-of-a-kind
                    pieces that perfectly reflect their individual style.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </Fragment>
  );
};
export default AboutUs;
