<#include "header.ftl">
<div class="am-cf admin-main">
    <!-- content start -->
    <div class="admin-content">
        <div class="admin-content-body">
            <div class="am-cf am-padding am-padding-bottom-0">
                <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">500</strong> / <small>That’s an error</small></div>
            </div>

            <hr>

            <div class="am-g">
                <div class="am-u-sm-12">
                    <h2 class="am-text-center am-text-xxxl am-margin-top-lg">500. Internal Server Error</h2>
                    <p class="am-text-center">${url}</p>
                    <p class="am-text-center">${exception}</p>

                </div>
            </div>
        </div>

        <footer class="admin-content-footer">
            <hr>
            <p class="am-padding-left">© 2014 AllMobilize, Inc. Licensed under MIT license.</p>
        </footer>
    </div>
    <!-- content end -->
</div>
<#include "footer.ftl">