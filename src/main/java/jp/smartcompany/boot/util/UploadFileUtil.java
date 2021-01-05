package jp.smartcompany.boot.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.bo.UploadFileInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class UploadFileUtil {

    private Boolean randomName = true;

    public String uploadRichTextImage(MultipartFile image,String configUploadPath) {
        ScCacheUtil cacheUtil = SpringUtil.getBean(ScCacheUtil.class);
        String uploadRootPath = cacheUtil.getSystemProperty(configUploadPath);
        if (Objects.isNull(uploadRootPath)) {
            throw new GlobalException("ファイル保存パスはまだ設定していません");
        }
        String originalFilename = image.getOriginalFilename();
        String originalName = originalFilename.substring(0,originalFilename.lastIndexOf("."));
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (!StrUtil.equalsAnyIgnoreCase(suffix,"jpg","jpeg","png","gif")) {
            throw new GlobalException("無効な画像サフィックス："+suffix);
        }
        // 没有存储文件夹的话先创建好存储文件夹
        createUploadFolder(uploadRootPath);
        UploadFileInfo uploadFileInfo = getRichTextImagePath(uploadRootPath,"." + suffix,originalName);
        String destFilename = uploadFileInfo.getRealPath();
        // 没有存储文件夹的话要先创建存储文件夹
        int filePathEndIndex = destFilename.lastIndexOf(File.separator);
        String destFilePath = destFilename.substring(0,filePathEndIndex);
        createUploadFolder(destFilePath);
        try {
            uploadImage2Local(image, destFilename);
        } catch(IOException e) {
            throw new GlobalException("ファイル保存失敗");
        }
        return uploadFileInfo.getFileUrl();
    }

    public List<UploadFileInfo> uploadAttachment(List<MultipartFile> files, String module, String configUploadPath) {
        ScCacheUtil cacheUtil = SpringUtil.getBean(ScCacheUtil.class);
        String uploadRootPath = cacheUtil.getSystemProperty(configUploadPath);
        List<UploadFileInfo> uploadFileInfos = CollUtil.newArrayList();
        if (Objects.isNull(uploadRootPath)) {
            throw new GlobalException("ファイル保存パスはまだ設定していません");
        }
        // 没有存储文件夹的话先创建好存储文件夹
        createUploadFolder(uploadRootPath);
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String originalName = originalFilename.substring(0,originalFilename.lastIndexOf("."));
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            UploadFileInfo uploadFileInfo = getPath(uploadRootPath,"." + suffix,module,originalName);
            System.out.println("++==");
            System.out.println(uploadFileInfo);
            String destFilename = uploadFileInfo.getRealPath();
            // 没有存储文件夹的话要先创建存储文件夹
            int filePathEndIndex = destFilename.lastIndexOf(File.separator);
            String destFilePath = destFilename.substring(0,filePathEndIndex);
            createUploadFolder(destFilePath);
            try {
                uploadImage2Local(file, destFilename);
            } catch(IOException e) {
                throw new GlobalException("ファイル保存失敗");
            }
            uploadFileInfos.add(uploadFileInfo);
        }
        return uploadFileInfos;
    }

    /**
     * 上传图片到本地
     * @param file 图片
     * @param filename 文件名
     * @throws IllegalStateException
     * @throws IOException
     */
    private void uploadImage2Local(MultipartFile file, String filename) throws IllegalStateException, IOException {
        file.transferTo(new File(filename));
    }

    /**
     * 富文本上传图片路径
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    private UploadFileInfo getRichTextImagePath(String prefix, String suffix,String originalName) {
        //文件路径
        UploadFileInfo uploadFileInfo = new UploadFileInfo();
        // 暂时写死路径中的模块名
        String path="";
        String timestampStr = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN);
        if (randomName) {
          //生成uuid
          String uuid = UUID.randomUUID().toString(true);
          path  += SecurityUtil.getUserId()+"-"+uuid+timestampStr;
        } else {
          path +=  SecurityUtil.getUserId()+"-"+originalName+"-"+timestampStr;
        }
        String realPath = "";
        String filename = "";
        if(StrUtil.isNotBlank(prefix)){
            filename = path+suffix;
            uploadFileInfo.setFilename(filename);
            realPath = prefix + File.separator + filename;
        }
        uploadFileInfo.setFileUrl("/upload/"+filename.replaceAll("\\\\","/"));
        uploadFileInfo.setRealPath(realPath);
        return uploadFileInfo;
    }

    /**
     * 文件路径
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    private UploadFileInfo getPath(String prefix, String suffix,String module,String originalName) {
        //文件路径
        UploadFileInfo uploadFileInfo = new UploadFileInfo();
        String path="";
        String timestampStr = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN);
        if (randomName) {
            //生成uuid
            String uuid = UUID.randomUUID().toString(true);
            path += module+"-"+SecurityUtil.getUserId()+"-"+uuid+timestampStr;
        } else {
            path += module+"-"+SecurityUtil.getUserId()+"-"+originalName+timestampStr;
        }
        String realPath = "";
        String filename = "";
        if(StrUtil.isNotBlank(prefix)){
            filename = path+suffix;
            uploadFileInfo.setFilename(filename);
            realPath = prefix + File.separator + filename;
        }
        uploadFileInfo.setFileUrl("/upload/"+filename.replaceAll("\\\\","/"));
        uploadFileInfo.setRealPath(realPath);
        return uploadFileInfo;
    }

    /**
     * 创建本地服务器上传文件所在父级文件夹
     * @param path 父级目录路径
     */
    public void createUploadFolder(String path) {
        File uploadPath = FileUtil.newFile(path);
        if (!uploadPath.exists()) {
            boolean createSuccess = uploadPath.mkdirs();
            if (!createSuccess){
                throw new GlobalException("upload folder作成失敗しました");
            }
        }
    }

    public void removePreFile(String parentFolder,String filename) {
        String realFilePath = parentFolder +File.separator + filename;
        File preAvatarFile = new File(realFilePath);
        if (preAvatarFile.exists() && !preAvatarFile.delete()) {
            throw new GlobalException("ファイル作成失敗");
        }
    }

    public void removePreFile(String filepath) {
        File preAvatarFile = new File(filepath);
        if (preAvatarFile.exists() && !preAvatarFile.delete()) {
            throw new GlobalException("ファイル削除失敗");
        }
    }

}
