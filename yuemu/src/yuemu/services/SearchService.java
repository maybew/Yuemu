package yuemu.services;

import java.util.List;

import org.json.JSONArray;

import yuemu.configure.Configure;
import yuemu.core.JSONUtils;
import yuemu.dao.search.DocumentSearchDAO;
import yuemu.dao.search.ImageSearchDAO;
import yuemu.dao.search.MusicSearchDAO;
import yuemu.dao.search.ResourceSearchDAO;
import yuemu.dao.search.VideoSearchDAO;

public class SearchService {

	public JSONArray SearchByTitles(List<String> titles, int type, int pageNum,
			int pageSize) {
		switch (type) {
		case Configure.ALL:
			return allByTitles(titles, pageNum, pageSize);
		case Configure.DOCUMENT:
			return documentByTitles(titles, pageNum, pageSize);
		case Configure.IMAGE:
			return imageByTitles(titles, pageNum, pageSize);
		case Configure.MUSIC:
			return musicByTitles(titles, pageNum, pageSize);
		case Configure.VIDEO:
			return videoByTitles(titles, pageNum, pageSize);
		}
		return null;
	}

	public JSONArray SearchByTags(List<String> tags, int type, int pageNum,
			int pageSize) {
		switch (type) {
		case Configure.ALL:
			return allByTags(tags, pageNum, pageSize);
		case Configure.DOCUMENT:
			return documentByTags(tags, pageNum, pageSize);
		case Configure.IMAGE:
			return imageByTags(tags, pageNum, pageSize);
		case Configure.MUSIC:
			return musicByTags(tags, pageNum, pageSize);
		case Configure.VIDEO:
			return videoByTags(tags, pageNum, pageSize);
		}
		return null;
	}

	public JSONArray SearchBoth(List<String> keywords, int type, int pageNum,
			int pageSize) {
		switch (type) {
		case Configure.ALL:
			return allByBoth(keywords, pageNum, pageSize);
		case Configure.DOCUMENT:
			return documentByBoth(keywords, pageNum, pageSize);
		case Configure.IMAGE:
			return imageByBoth(keywords, pageNum, pageSize);
		case Configure.MUSIC:
			return musicByBoth(keywords, pageNum, pageSize);
		case Configure.VIDEO:
			return videoByBoth(keywords, pageNum, pageSize);
		}
		return null;
	}

	/**
	 * 5个资源对应的生成JASONArray的方法通过titles搜索
	 * 
	 * @param titles
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	private JSONArray allByTitles(List<String> titles, int pageNum, int pageSize) {
		return JSONUtils
				.toJSONArray(ResourceSearchDAO
						.searchByTitleKeywordsJoiningWithAnd(titles, pageNum,
								pageSize));
	}

	private JSONArray documentByTitles(List<String> titles, int pageNum,
			int pageSize) {
		return JSONUtils
				.toJSONArray(DocumentSearchDAO
						.searchByTitleKeywordsJoiningWithAnd(titles, pageNum,
								pageSize));
	}

	private JSONArray imageByTitles(List<String> titles, int pageNum,
			int pageSize) {
		return JSONUtils
				.toJSONArray(ImageSearchDAO
						.searchByTitleKeywordsJoiningWithAnd(titles, pageNum,
								pageSize));
	}

	private JSONArray musicByTitles(List<String> titles, int pageNum,
			int pageSize) {
		return JSONUtils
				.toJSONArray(MusicSearchDAO
						.searchByTitleKeywordsJoiningWithAnd(titles, pageNum,
								pageSize));
	}

	private JSONArray videoByTitles(List<String> titles, int pageNum,
			int pageSize) {
		return JSONUtils
				.toJSONArray(VideoSearchDAO
						.searchByTitleKeywordsJoiningWithAnd(titles, pageNum,
								pageSize));
	}

	/**
	 * 5个资源对应的生成JASONArray的方法通过tags搜索
	 * 
	 * @param tags
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	private JSONArray allByTags(List<String> tags, int pageNum, int pageSize) {
		return JSONUtils.toJSONArray(ResourceSearchDAO
				.searchByTagKeywordsJoiningWithAnd(tags, pageNum, pageSize));
	}

	private JSONArray documentByTags(List<String> tags, int pageNum,
			int pageSize) {
		return JSONUtils.toJSONArray(DocumentSearchDAO
				.searchByTagKeywordsJoiningWithAnd(tags, pageNum, pageSize));
	}

	private JSONArray imageByTags(List<String> tags, int pageNum, int pageSize) {
		return JSONUtils.toJSONArray(ImageSearchDAO
				.searchByTagKeywordsJoiningWithAnd(tags, pageNum, pageSize));
	}

	private JSONArray musicByTags(List<String> tags, int pageNum, int pageSize) {
		return JSONUtils.toJSONArray(MusicSearchDAO
				.searchByTagKeywordsJoiningWithAnd(tags, pageNum, pageSize));
	}

	private JSONArray videoByTags(List<String> tags, int pageNum, int pageSize) {
		return JSONUtils.toJSONArray(VideoSearchDAO
				.searchByTagKeywordsJoiningWithAnd(tags, pageNum, pageSize));
	}

	/**
	 * 5个资源对应的生成JASONArray的方法通过keywords搜索
	 * 
	 * @param keywords
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	private JSONArray allByBoth(List<String> keywords, int pageNum, int pageSize) {
		return JSONUtils
				.toJSONArray(ResourceSearchDAO
						.searchByAllKeywordsJoiningWithAnd(keywords, pageNum,
								pageSize));
	}

	private JSONArray documentByBoth(List<String> keywords, int pageNum,
			int pageSize) {
		return JSONUtils
				.toJSONArray(DocumentSearchDAO
						.searchByAllKeywordsJoiningWithAnd(keywords, pageNum,
								pageSize));
	}

	private JSONArray imageByBoth(List<String> keywords, int pageNum,
			int pageSize) {
		return JSONUtils
				.toJSONArray(ImageSearchDAO.searchByAllKeywordsJoiningWithAnd(
						keywords, pageNum, pageSize));
	}

	private JSONArray musicByBoth(List<String> keywords, int pageNum,
			int pageSize) {
		return JSONUtils
				.toJSONArray(MusicSearchDAO.searchByAllKeywordsJoiningWithAnd(
						keywords, pageNum, pageSize));
	}

	private JSONArray videoByBoth(List<String> keywords, int pageNum,
			int pageSize) {
		return JSONUtils
				.toJSONArray(VideoSearchDAO.searchByAllKeywordsJoiningWithAnd(
						keywords, pageNum, pageSize));
	}
}
