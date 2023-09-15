import React from "react";
import sitter from '../../img/hero-sitter.png';
import lamp from '../../img/hero-lamp.png';
import  Button  from "react-bootstrap/Button";
const Hero = (props) => {
    const {HomeCss} = props;
    return(
        <section>
            <main className={`${HomeCss.twoColums}`}>
                <article className={`${HomeCss.heroTitleSection}`}>
                    <div className={`${HomeCss.heroTextContainer}`}>
                        <h1 className={`${HomeCss.heroTextContainerTitle}`}>welcome to cedarXpress application</h1>
                        <p className={`${HomeCss.heroTextContainerText}`}>
                            Far far away, behind the word mountains, far from the countries Vokalia and Consonantia
                        </p>
                        <Button className={`${HomeCss.actionBtn}`} variant="dark">show more</Button>
                    </div>
                </article>
                <article className={`${HomeCss.heroImageSection}`}>
                    <img src={sitter} alt='sitter' className={`${HomeCss.heroImages} ${sitter}}`}/>
                    <img src={lamp} alt='lamp' className={`${HomeCss.heroImages} ${HomeCss.lamp}`}/>
                </article>
            </main>
        </section>
    )
}

export default Hero;