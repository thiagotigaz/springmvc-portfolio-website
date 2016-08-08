(function ($, window, document, undefined) {

    'use-strict';

    var winWidth, socialHeight;

    function setWidth() {

        winWidth = $(window).innerWidth(); //This may need to be width()	
        socialHeight = (winWidth > 640) ? 120 : 160;

    }

    setWidth();

    $(window).on('resize', setWidth);
    //Dom Ready
    $(function () {

        var didScroll = false,
            icon = $(".huge-title, #godown"),
            $window = $(window);

        $(window).scroll(function () {
            didScroll = true;
        });

        window.setInterval(function () {
            if (didScroll) {
                if (1 - $window.scrollTop() / 200 > -20) {
                    icon.css({
                        opacity: 1 - $window.scrollTop() / 500
                    });
                }
                didScroll = false;
            }
        }, 50);

        //Social Scroll
        $(window).scroll(function () {
            if ($(window).scrollTop() < 300) {
                $('#socialsection').css({
                    opacity: "0"
                }, 500);
            } else if ($(window).scrollTop() > 300) {
                $('#socialsection').css({
                    opacity: "1"
                }, 500);
            }
        });

        //Menu toggle
        $('#menu-button').on('click', function () {
            $('.nav').animate({
                height: 'toggle'
            });
        });

        $('#socialsection').on('click', function () {

            if ($(this).hasClass('close-social')) {
                $(this).animate({
                    height: 40
                }, 200).removeClass('close-social');
            } else {
                $(this).animate({
                    height: socialHeight
                }, 200).addClass('close-social');
            }
        });

        //Knob
        $(".knob").knob({
            change: function (value) {
                //console.log("change : " + value);
            },
            release: function (value) {
                //console.log(this.$.attr('value'));
                console.log("release : " + value);
            },
            cancel: function () {
                console.log("cancel : ", this);
            },
            draw: function () {

                // "cr3ativ" case
                if (this.$.data('skin') == 'cr3ativ') {

                    var a = this.angle(this.cv) // Angle
                    ,
                        sa = this.startAngle // Previous start angle
                        ,
                        sat = this.startAngle // Start angle
                        ,
                        ea // Previous end angle
                        , eat = sat + a // End angle
                        ,
                        r = 1;

                    this.g.lineWidth = this.lineWidth;

                    this.o.cursor && (sat = eat - 0.3) && (eat = eat + 0.3);

                    if (this.o.displayPrevious) {
                        ea = this.startAngle + this.angle(this.v);
                        this.o.cursor && (sa = ea - 0.3) && (ea = ea + 0.3);
                        this.g.beginPath();
                        this.g.strokeStyle = this.pColor;
                        this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, sa, ea, false);
                        this.g.stroke();
                    }

                    this.g.beginPath();
                    this.g.strokeStyle = r ? this.o.fgColor : this.fgColor;
                    this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, sat, eat, false);
                    this.g.stroke();

                    this.g.lineWidth = 2;
                    this.g.beginPath();
                    this.g.strokeStyle = this.o.fgColor;
                    this.g.arc(this.xy, this.xy, this.radius - this.lineWidth + 1 + this.lineWidth * 2 / 3, 0, 2 * Math.PI, false);
                    this.g.stroke();

                    return false;
                }
            }
        });

        //plusAnchor
        $('body').plusAnchor({
            easing: 'easeInOutExpo',
            offsetTop: -75,
            speed: 800,
            onInit: function (base) {

                if (base.initHash != '' && $(base.initHash).length > 0) {
                    window.location.hash = 'hash_' + base.initHash.substring(1);
                    window.scrollTo(0, 0);

                    $(window).load(function () {

                        timer = setTimeout(function () {
                            $(base.scrollEl).animate({
                                scrollTop: $(base.initHash).offset().top
                            }, base.options.speed, base.options.easing);
                        }, 2000); // setTimeout
                    }); // window.load
                }; // if window.location.hash
            } // onInit
        });

        //Video Wallpaper Settings - alter the URL's to your converted videos		
        $("#video_element").wallpaper({
                source: "/img/backgroundweb7.jpg"
        });

        //fitVids
        $(".video-container").fitVids();

        //fancybox
        $(".fancybox").fancybox();

        //ScrolltoTop
        $("#toTop").scrollToTop(1000);

        //owlCarousel - these settings are for the testimonials and sub heading under your name title at the top
        $(".testimonials").owlCarousel({

            // Most important owl features
            items: 1,
            itemsCustom: false,
            itemsDesktop: [1199, 1],
            itemsDesktopSmall: [980, 1],
            itemsTablet: [768, 1],
            itemsTabletSmall: false,
            itemsMobile: [479, 1],
            singleItem: false,
            itemsScaleUp: false,

            //Basic Speeds - set your speeds in milliseconds here!
            slideSpeed: 400,
            paginationSpeed: 500,
            rewindSpeed: 1000,

            //Autoplay
            autoPlay: true,
            stopOnHover: false

        });
        //owlCarousel - these settings are for the client logos
        $(".owl-example").owlCarousel({

            // Most important owl features
            items: 4,
            itemsCustom: false,
            itemsDesktop: [1199, 2],
            itemsDesktopSmall: [980, 1],
            itemsTablet: [768, 2],
            itemsTabletSmall: false,
            itemsMobile: [479, 1],
            singleItem: false,
            itemsScaleUp: false,

            //Basic Speeds - set your speeds in milliseconds here!
            slideSpeed: 400,
            paginationSpeed: 800,
            rewindSpeed: 1000,

            //Autoplay
            autoPlay: true,
            stopOnHover: true,

            // Navigation
            navigation: false,
            navigationText: ["prev", "next"],
            rewindNav: true,
            scrollPerPage: false,

            //Pagination
            pagination: true,
            paginationNumbers: false,

            // Responsive 
            responsive: true,
            responsiveRefreshRate: 200,
            responsiveBaseWidth: window,

            // CSS Styles
            baseClass: "owl-carousel",
            theme: "owl-theme",

            //Lazy load
            lazyLoad: false,
            lazyFollow: true,
            lazyEffect: "fade",

            //Auto height
            autoHeight: true,

            //JSON 
            jsonPath: false,
            jsonSuccess: false,

            //Mouse Events
            dragBeforeAnimFinish: true,
            mouseDrag: true,
            touchDrag: true,

            //Transitions
            transitionStyle: false,

            // Other
            addClassActive: false,

            //Callbacks
            beforeUpdate: false,
            afterUpdate: false,
            beforeInit: false,
            afterInit: false,
            beforeMove: false,
            afterMove: false,
            afterAction: false,
            startDragging: false,
            afterLazyLoad: false

        });

        //Contact Form
        $(document).on('submit', 'form#contact_form', function (e) {
        
            e.preventDefault();
        
            $('form#contact_form .error').remove();
        
            var hasError = false;
        
            $('.requiredField').each(function () {
        
                if ($.trim($(this).val()) == '') {
        
                    var labelText = $(this).prev('label').text();
        
                    $(this).parent().append('<span class="error">Please complete the required fields.</span>');
        
                    $(this).addClass('inputError');
        
                    hasError = true;
        
                } else if ($(this).hasClass('email')) {
        
                    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
        
                    if (!emailReg.test($.trim($(this).val()))) {
        
                        var labelText = $(this).prev('label').text();
         
						$(this).parent().append('<span class="error">You entered an invalid email</span>');
                 
                        $(this).addClass('inputError');
                 
                        hasError = true;
                 
                    }
                
                }
            
            });
            
            if (!hasError) {
            
                $('form#contact_form input.submit').fadeOut('normal', function () {
            
                    $(this).parent().append('');
            
                });
            
                var formInput = $(this).serialize();
            
                $.post($(this).attr('action'), formInput, function (data) {
            
                    $('form#contact_form').slideUp("fast", function () {
            
                        $(this).before('<p class="success">Thank you! Your email was successfully sent. I will contact you as soon as possible.</p>');
            
                    });
            
                });
            
            }

            return false;

        });
        
        $("#go-top span").hover(function() {
                $(this).removeClass("glyphicon-arrow-up").addClass("glyphicon-upload");
        }, function() {
                $(this).removeClass("glyphicon-upload").addClass("glyphicon-arrow-up");
        })
        
        new WOW().init();
    });

})(window.jQuery, this, document);