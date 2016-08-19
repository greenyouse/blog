;;; Anyone following along, evaluate both of these sexprs with C-c md and then
;;; call org-publish within THIS FILE.

;;; MathJax config, for install see: http://docs.mathjax.org/en/latest/installation.html
;;; For including ClojureScript, add #+HTML: with a <script> pointing to the
;;;  output js file for that page

;; set html options here (they'll be added to all output files)
(setq org-html-preamble "<nav class=\"navbar navbar-default\">
    <div class=\"container-fluid\">
      <div class=\"navbar-header\">
        <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#main-navbar\">
          <span class=\"sr-only\">Toggle navigation</span>
          <span class=\"icon-bar\"></span>
          <span class=\"icon-bar\"></span>
          <span class=\"icon-bar\"></span>
        </button>
        <a class=\"navbar-brand\" href=\"/index.html\">EDBABCOCK</a>
      </div>
      <div class=\"collapse navbar-collapse\" id=\"main-navbar\">
        <ul class=\"nav navbar-nav\">
          <li><a href=\"/about.html\">About</a></li>
          <li><a href=\"categories.html\">Categories</a></li>
        </ul>
      </div>
    </div>
  </nav>"

      org-html-postamble "<footer> <hr />
                         <p><a href='http://creativecommons.org/licenses/by-nc-sa/3.0/' target='_blank'>©copyright 2015</a>, by <a href='/about.html'>Ed Babcock</a> (^_^)v</p>
                         </footer>
                         <script type='text/javascript' src='js/jquery.min.js'></script>
                         <script type='text/javascript' src='js/bootstrap.min.js'></script>"
      org-html-head "<meta name='viewport' content='idth=device-width' initial-scale=1>
                     <link rel='stylesheet' href='/css/bootstrap.min.css' type='text/css'/>
                     <link rel='stylesheet' href='/css/blog.css' type='text/css'/>
                     <script type='text/javascript' src='MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML'></script>"
      user-full-name "Ed Babcock (greenyouse)")

(setq org-publish-project-alist
      '(("orgfiles"
         :base-directory "~/Gits/personal/Blog/resources/www/"
         :base-extension "org\\|md"
         :recursive t
         :publishing-directory "~/Gits/personal/Blog/resources/public/"
         :section-numbers nil
         :auto-sitemap nil
         :sitemap-title "Archive"
         :sitemap-filename "archive.org"
         :sitemap-sort-files "chronologically"
         :description "Hacks, projects, and general butt-kicking with Ed (greenyouse)"
         :keywords "Clojure, ClojureScript, greenyouse, LISP"
         :with-toc nil
         :publishing-function org-html-publish-to-html
         :html-html5-fancy t
         :html-mathjax "path:MathJax/MathJax.js" ; NOTE: you'll need this and the MathJax script tag in head for rendering correctly
         )
        ("org-static"
         :base-directory ""
         :base-extension "css\\|js\\|png\\|svg\\|jpg\\|gif\\|pdf"
         :publishing-directory "public/"
         :recursive t
         :publishing-function org-publish-attachment)
        ("pwa" :components ("orgfiles" "org-static"))))


;; TODO: do a new hook for the content section to make bootrap stuff easier
;; TODO: favicon
