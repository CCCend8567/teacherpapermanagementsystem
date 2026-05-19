// static/js/main.js

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    // 添加导航栏滚动效果
    window.addEventListener('scroll', function() {
        const header = document.querySelector('header');
        if (window.scrollY > 50) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });

    // 表单提交处理
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            // 简单的表单验证示例
            const requiredFields = form.querySelectorAll('[required]');
            let isValid = true;
            
            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    isValid = false;
                    showError(field, '此字段不能为空');
                } else {
                    hideError(field);
                }
            });
            
            if (!isValid) {
                e.preventDefault();
            }
        });
    });

    // 文件上传预览功能
    const fileInputs = document.querySelectorAll('input[type="file"]');
    fileInputs.forEach(input => {
        input.addEventListener('change', function() {
            const file = this.files[0];
            if (file) {
                // 验证文件大小（10MB）
                const maxSize = 10 * 1024 * 1024; // 10MB
                if (file.size > maxSize) {
                    alert('文件大小不能超过10MB，当前文件大小: ' + 
                          (file.size / (1024 * 1024)).toFixed(2) + 'MB');
                    this.value = '';
                    return;
                }
                
                // 验证文件类型
                if (!file.name.toLowerCase().endsWith('.pdf')) {
                    alert('只支持上传PDF格式的文件');
                    this.value = '';
                    return;
                }
                
                // 显示文件名
                const fileName = this.nextElementSibling;
                if (fileName && fileName.classList.contains('file-name')) {
                    fileName.textContent = file.name + ' (' + (file.size / (1024 * 1024)).toFixed(2) + 'MB)';
                }
            }
        });
    });
});

// 显示错误信息
function showError(field, message) {
    let errorElement = field.nextElementSibling;
    
    if (!errorElement || !errorElement.classList.contains('error-message')) {
        errorElement = document.createElement('div');
        errorElement.className = 'error-message';
        field.parentNode.insertBefore(errorElement, field.nextSibling);
    }
    
    errorElement.textContent = message;
    field.classList.add('error');
}

// 隐藏错误信息
function hideError(field) {
    const errorElement = field.nextElementSibling;
    
    if (errorElement && errorElement.classList.contains('error-message')) {
        errorElement.remove();
    }
    
    field.classList.remove('error');
}

// 添加平滑滚动效果
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        
        const targetId = this.getAttribute('href');
        if (targetId === '#') return;
        
        const targetElement = document.querySelector(targetId);
        if (targetElement) {
            targetElement.scrollIntoView({
                behavior: 'smooth'
            });
        }
    });
});