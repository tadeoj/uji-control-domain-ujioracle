package es.uji.control.domain.ujioracle.internal.people;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import es.uji.control.domain.people.IPhoto;
import es.uji.control.domain.provider.service.connectionfactory.ControlConnectionException;
import es.uji.control.domain.provider.subsystem.people.IPhotoStream;

public class AllPhotosCall {

	final static int BLOCK_SIZE = 500;

	final private PhotosConnectionContext context;

	final private IPhotoStream photoStream;

	public AllPhotosCall(PhotosConnectionContext context, IPhotoStream photoStream) {
		this.context = context;
		this.photoStream = photoStream;
	}

	public void run() throws ControlConnectionException {
		try {
			ResultSet rset = context.getAllPhotosResultSet();

			for (;;) {
				List<IPhoto> block = getAllPhotos(rset);
				if (block.size() == 0) {
					photoStream.onCompleted();
					break;
				} else {
					photoStream.onNext(block);
				}
			}
		} catch (Exception ex) {
			photoStream.onError(ex);
		}
	}

	private List<IPhoto> getAllPhotos(ResultSet rset) throws Exception {
		ArrayList<IPhoto> photos = new ArrayList<>(1);

		while (rset.next() && photos.size() < BLOCK_SIZE) {

			
		}

		return photos;

	}

}
